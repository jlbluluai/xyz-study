## 说明

### 版本1

基于内存 Queue 实现生产和消费 API（已经完成） 

- 创建内存 BlockingQueue，作为底层消息存储 
- 定义 Topic，支持多个 Topic 
- 定义 Producer，支持 Send 消息 
- 定义Consumer，支持Poll消息

### 版本2

去掉内存 Queue，设计自定义 Queue，实现消息确认和消费 offset 

- 自定义内存 Message 数组模拟 Queue。 
- 使用指针记录当前消息写入位置。 
- 对于每个命名消费者，用指针记录消费位置。