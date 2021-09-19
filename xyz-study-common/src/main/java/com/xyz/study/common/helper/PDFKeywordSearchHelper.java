package com.xyz.study.common.helper;

import com.xyz.study.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xyz
 * @date 2021/9/9
 **/
@Slf4j
public class PDFKeywordSearchHelper {

    public static void main(String[] args) throws Exception {
        String directory = "/Users/zhuweijie/filehome/投资/财报/2020/年报";
        List<String> list = FileUtils.scanDirectory(directory);

        List<String> qualified = new ArrayList<>();
        list.forEach(e -> {
            if (e.contains("pdf")) {
                String filePath = directory + "/" + e;
                boolean flag = containsKeyword(filePath, "标准无保留意见");
                if (flag) {
                    qualified.add(e);
                }
            }
        });

        log.info("合格的文件:{}", qualified);

        if (!CollectionUtils.isEmpty(qualified)) {
            String qualifiedDirectory = "/Users/zhuweijie/filehome/投资/财报/tmp";
            qualified.forEach(e -> {
                FileUtils.copy(directory + "/" + e, qualifiedDirectory + "/" + e);
            });
        }

    }

    public static boolean containsKeyword(String filePath, String keyword) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                String pdfFileInText = tStripper.getText(document);
//                String[] lines = pdfFileInText.split("\\r?\\n");
//                for (String line : lines) {
//
//                }
                if (pdfFileInText.contains(keyword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
