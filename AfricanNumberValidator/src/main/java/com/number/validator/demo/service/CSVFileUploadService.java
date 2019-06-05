package com.number.validator.demo.service;

import com.number.validator.demo.entity.request.MobileNumberRequest;
import com.number.validator.demo.exception.InvalidFileFormatException;
import com.number.validator.demo.exception.NotAcceptableFileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CSVFileUploadService {
    private static final String COMMA = ",";
    private static final String EXTENSION = "csv";
    private static final Logger logger = LoggerFactory.getLogger(CSVFileUploadService.class);

    public List<MobileNumberRequest> processCsvFile(MultipartFile file) throws InvalidFileFormatException, NotAcceptableFileExtension{
        if(!checkIfValid(file.getOriginalFilename()))
            throw new NotAcceptableFileExtension();

        List<MobileNumberRequest> inputList = new ArrayList<>();
        try{
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
            logger.info("CSV file parsed.");
        } catch (Exception e) {
            logger.info("CSV file could not parsed");
            throw new InvalidFileFormatException();
        }

        return inputList;
    }

    private boolean checkIfValid(String name){
        if(name.contains(".") && name.lastIndexOf('.')!= 0){
            String fileExtension=name.substring(name.lastIndexOf('.')+1);
            if(EXTENSION.equals(fileExtension))
                return true;
        }
        return false;
    }


    private Function<String, MobileNumberRequest> mapToItem = line -> {
        String[] p = line.split(COMMA);
        MobileNumberRequest mobileNumberRequest = new MobileNumberRequest();
        mobileNumberRequest.setId(p[0]);
        if (p[1] != null && p[1].trim().length() > 0) {
            mobileNumberRequest.setSmsPhone(p[1]);
        }

        return mobileNumberRequest;
    };
}
