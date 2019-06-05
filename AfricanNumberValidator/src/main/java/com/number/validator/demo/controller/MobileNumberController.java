package com.number.validator.demo.controller;

import com.number.validator.demo.entity.request.MobileNumberRequest;
import com.number.validator.demo.exception.InvalidFileFormatException;
import com.number.validator.demo.exception.NotAcceptableFileExtension;
import com.number.validator.demo.service.StatisticalResultCreator;
import com.number.validator.demo.service.CSVFileUploadService;
import com.number.validator.demo.service.MobileNumberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MobileNumberController {
    private static final Logger logger = LoggerFactory.getLogger(MobileNumberController.class);

    @Autowired
    private CSVFileUploadService csvFileUploadService;

    @Autowired
    private MobileNumberService mobileNumberService;

    private StatisticalResultCreator statisticalResultCreator;


    @PostMapping(value = "/validateNumbers", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity validateNumbers(@RequestParam("file") MultipartFile file){
        try{
            List<MobileNumberRequest> mobileNumberRequests = csvFileUploadService.processCsvFile(file);
            statisticalResultCreator = new StatisticalResultCreator();

            if(mobileNumberService.convertToMobileNumber(mobileNumberRequests, statisticalResultCreator, true)){
                logger.info("ValidateNumbers operation was successful");
                return ResponseEntity.ok().body(statisticalResultCreator.createResponse());
            }
        }
        catch (InvalidFileFormatException e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        catch(NotAcceptableFileExtension e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
        }
        catch(Exception e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        logger.info("ValidateNumbers operation was not successful.");
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Operation could not performed");
    }


    @PostMapping(value = "/validateNumber", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity validateNumber(@RequestParam("file") MultipartFile file){
        try{
            List<MobileNumberRequest> mobileNumberRequests = csvFileUploadService.processCsvFile(file);
            statisticalResultCreator = new StatisticalResultCreator();

            if(mobileNumberRequests.size()>1){
                logger.info("There is more than one number. ValidateNumbers should be used.");
                return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("There are more than one phone Number");
            }

            if(mobileNumberService.convertToMobileNumber(mobileNumberRequests, statisticalResultCreator, false)){
                logger.info("ValidateNumber operation was successful");
                return ResponseEntity.ok().body(statisticalResultCreator.createResponseForOne());
            }
        } catch (InvalidFileFormatException e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        catch(NotAcceptableFileExtension e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
        }
        catch(Exception e){
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        logger.info("ValidateNumber operation was not successful.");
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Operation could not performed");
    }
}
