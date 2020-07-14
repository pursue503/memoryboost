package com.memoryboost.service.admin;

import com.memoryboost.domain.dto.product.request.ProductDetailSaveRequestDTO;
import com.memoryboost.domain.dto.product.request.ProductSaveRequestDTO;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductImage;
import com.memoryboost.domain.entity.product.ProductImageRepository;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.entity.product.detail.cpu.CpuRepository;
import com.memoryboost.domain.entity.product.detail.hdd.HddRepository;
import com.memoryboost.domain.entity.product.detail.keyboard.KeyboardRepository;
import com.memoryboost.domain.entity.product.detail.memory.MemoryRepository;
import com.memoryboost.domain.entity.product.detail.monitor.Monitor;
import com.memoryboost.domain.entity.product.detail.monitor.MonitorRepository;
import com.memoryboost.domain.entity.product.detail.motherboard.MotherboardRepository;
import com.memoryboost.domain.entity.product.detail.mouse.MouseRepository;
import com.memoryboost.domain.entity.product.detail.power.PowerRepository;
import com.memoryboost.domain.entity.product.detail.ssd.SsdRepository;
import com.memoryboost.domain.entity.product.detail.tbcase.CaseRepository;
import com.memoryboost.domain.entity.product.detail.vga.VgaRepository;
import com.memoryboost.util.product.ProductS3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    /*
    productCategory cpu부터 monitor 까지 1~
     */

    private final CpuRepository cpuRepository;
    private final MotherboardRepository motherboardRepository;
    private final VgaRepository vgaRepository;
    private final MemoryRepository memoryRepository;
    private final HddRepository hddRepository;
    private final SsdRepository ssdRepository;
    private final CaseRepository caseRepository;
    private final PowerRepository powerRepository;
    private final KeyboardRepository keyboardRepository;
    private final MouseRepository mouseRepository;
    private final MonitorRepository monitorRepository;

    //S3 파일 업로드를 해줄 util
    private final ProductS3Uploader productS3Uploader;

    //상품 썸넹닐 이미지 S3경로
    private final String thumbnailPath = "product/thumbnail";

    //상품 상세 이미지 S3 경로
    private final String detailImagePath = "product/detail";

    //S3주소 잘라내기위함
    private final String s3Url = "https://memoryboost-service-build.s3.ap-northeast-2.amazonaws.com";

    @Transactional
    public boolean productUpload(ProductSaveRequestDTO productSaveRequestDTO, MultipartFile thumbnailFile,
                                 List<MultipartFile> detailImageList, ProductDetailSaveRequestDTO productDetailSaveRequestDTO){
        try{
            //product 테이블 저장시작.
            //파일 이름 변경
            String thumbnailReName = productS3Uploader.fileReName(thumbnailFile);

            //thumbnail S3에 업로드한후 URL 가져오기
            String thumbnailUrl = productS3Uploader.upload(thumbnailFile,thumbnailPath,thumbnailReName);

            //URL DTO에 저장 , URL S3주소 삭제
            productSaveRequestDTO.setProductThumbnail(thumbnailUrl.replace(s3Url,""));

            //product  save 하고 ID번호를 얻기위해 객체로 리턴받음
            Product product = productRepository.save(productSaveRequestDTO.toEntity());

            //상품 상세 이미지 저장
            for (MultipartFile detailImage : detailImageList) {
                //파일 이름 변경
                String detailImageRename = productS3Uploader.fileReName(detailImage);

                //S3에 이미지 업로드 한후 URL 가져오기
                String detailImageUrl =  productS3Uploader.upload(detailImage,detailImagePath,detailImageRename);
                //상품 상세이미지 저장
                productImageRepository.save(ProductImage.builder().productNo(product).productImagePath(detailImageUrl.replace(s3Url,"")).build());
            }

            //상품 세부내용 저장

            switch (product.getProductCategory()) {

                case 1:
                    //cpu
                    cpuRepository.save(productDetailSaveRequestDTO.cpuEntity(product));
                    break;

                case 2:
                    //motherboard
                    motherboardRepository.save(productDetailSaveRequestDTO.motherboardEntity(product));
                    break;

                case 3:
                    //vga
                    vgaRepository.save(productDetailSaveRequestDTO.vgaEntity(product));
                    break;

                case 4:
                    //memory
                    memoryRepository.save(productDetailSaveRequestDTO.memoryEntity(product));
                    break;

                case 5:
                    //hdd
                    hddRepository.save(productDetailSaveRequestDTO.hddEntity(product));
                    break;

                case 6:
                    //ssd
                    ssdRepository.save(productDetailSaveRequestDTO.ssdEntity(product));
                    break;

                case 7:
                    //case
                    caseRepository.save(productDetailSaveRequestDTO.caseEntity(product));
                    break;

                case 8:
                    //power
                    powerRepository.save(productDetailSaveRequestDTO.powerEntity(product));
                    break;

                case 9:
                    //keyboard
                    keyboardRepository.save(productDetailSaveRequestDTO.keyboardEntity(product));
                    break;

                case 10:
                    //mouse
                    mouseRepository.save(productDetailSaveRequestDTO.mouseEntity(product));
                    break;

                case 11:
                    //monitor
                    monitorRepository.save(productDetailSaveRequestDTO.monitorEntity(product));
                    break;

                default:
                    return false;

            }// case end

        } catch (Exception e) {
            log.info("에러에러");
            log.info(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
