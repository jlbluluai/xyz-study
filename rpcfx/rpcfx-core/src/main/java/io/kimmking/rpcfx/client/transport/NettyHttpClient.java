package io.kimmking.rpcfx.client.transport;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;

@Slf4j
public class NettyHttpClient {


    public RpcfxResponse post(String url, RpcfxRequest rpcfxRequest) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 取host和ip
            URL netUrl = new URL(url);
            String host = netUrl.getHost();
            int port = netUrl.getPort();

            NettyHttpClientHandler clientHandler = new NettyHttpClientHandler();

            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            ch.pipeline().addLast(new HttpObjectAggregator(1024*1024));
                            ch.pipeline().addLast(clientHandler);
                        }
                    });

            // Start the client.
            ChannelFuture cf = b.connect().sync();

            // 发送请求
            URI uri = new URI(url);
            FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,
                    HttpMethod.POST,
                    uri.toASCIIString(),
                    Unpooled.wrappedBuffer(JSON.toJSONBytes(rpcfxRequest)));
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);

            // 等待结果返回
            ChannelPromise channelPromise = clientHandler.sendMessage(request);
            channelPromise.await();
            RpcfxResponse rpcfxResponse = clientHandler.getRpcfxResponse();

            cf.channel().closeFuture().sync();
            return rpcfxResponse;
        } catch (Exception e) {
            log.error("netty post error", e);
            return null;
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}