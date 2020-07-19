package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.entity.product.detail.cpu.QCpu;
import com.memoryboost.domain.entity.product.detail.hdd.QHdd;
import com.memoryboost.domain.entity.product.detail.keyboard.QKeyboard;
import com.memoryboost.domain.entity.product.detail.memory.QMemory;
import com.memoryboost.domain.entity.product.detail.monitor.QMonitor;
import com.memoryboost.domain.entity.product.detail.motherboard.QMotherboard;
import com.memoryboost.domain.entity.product.detail.mouse.QMouse;
import com.memoryboost.domain.entity.product.detail.power.QPower;
import com.memoryboost.domain.entity.product.detail.ssd.QSsd;
import com.memoryboost.domain.entity.product.detail.tbcase.QCase;
import com.memoryboost.domain.entity.product.detail.vga.QVga;
import com.memoryboost.domain.entity.product.review.QProductReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {



    private final JPAQueryFactory queryFactory;

    @Override
    public QueryResults<Product> findByProductNameList(List<String> productNameList) {
        QProduct produt = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        for (String s : productNameList) {
            builder.and(produt.productName.contains(s));
        }
        return queryFactory.selectFrom(produt)
                .where(builder)
                .offset(0)
                .limit(10)
                .fetchResults();
    }

    @Override
    public List<ProductSearchResponseDTO> productSearch(String[] searchArr, String order , int page) {

        QProduct product = QProduct.product;
        QProductReview productReview = QProductReview.productReview;

        BooleanBuilder builder = new BooleanBuilder();
        for(String name : searchArr) {
            builder.and(product.productName.contains(name));
        }
        //동적 order by
        JPAQuery<ProductSearchResponseDTO> jpaQuery = queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
                product.productNo,product.productName,
                product.productCategory,product.productDescription,
                product.productThumbnail,product.productPrice,
                productReview.reviewGrade.avg().as("reviewGradeAvg"),productReview.reviewNo.count().as("reviewCount")))
                .from(product)
                .leftJoin(productReview).on(product.eq(productReview.productNo))
                .where(builder)
                .groupBy(product.productNo,product.productName,
                        product.productCategory,product.productDescription,
                        product.productThumbnail,product.productPrice);
        //order 에 따라서 order by 를 다르게
        switch (order.toLowerCase()) {
            case "popular" :
                jpaQuery.orderBy(productReview.reviewGrade.avg().desc());
                break;
            case "pricedesc":
                jpaQuery.orderBy(product.productPrice.desc());
                break;
            case "priceasc" :
                jpaQuery.orderBy(product.productPrice.asc());
                break;
        }

        jpaQuery.offset((page -1 ) * 10).limit(10);

        List<ProductSearchResponseDTO> responseDTOList = jpaQuery.fetch();

        return responseDTOList;


//        return queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
//                product.productNo,product.productName,
//                product.productCategory,product.productDescription,
//                product.productThumbnail,product.productPrice,
//                productReview.reviewGrade.avg().as("reviewGradeAvg"),productReview.reviewNo.count().as("reviewCount")))
//                .from(product)
//                .leftJoin(productReview).on(product.eq(productReview.productNo))
//                .where(builder)
//                .groupBy(product.productNo,product.productName,
//                        product.productCategory,product.productDescription,
//                        product.productThumbnail,product.productPrice)
//                .offset(0)
//                .limit(10)
//                .fetch();
    }

    @Override
    public int countByProductNameContaining(String[] searchArr) {

        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        for(String name : searchArr) {
            builder.and(product.productName.contains(name));
        }

        return (int) queryFactory.select(product.count()).from(product)
                .where(builder).fetchCount();
    }

    @Override
    public ArrayList<String> searchPreview(String keyword) {
        QProduct product = QProduct.product;
        return (ArrayList<String>) queryFactory.select(product.productName)
                .from(product)
                .where(product.productName.contains(keyword))
                .fetch();
    }

    @Override
    public List<ProductSearchResponseDTO> productFilterSearch(ProductFilterSearchRequestDTO filterDTO, String order , int page) {
        QProduct product = QProduct.product;
        QProductReview productReview = QProductReview.productReview;

        QCpu cpu = QCpu.cpu;
        QMotherboard motherboard = QMotherboard.motherboard;
        QVga vga = QVga.vga;
        QMemory memory = QMemory.memory;
        QHdd hdd = QHdd.hdd;
        QSsd ssd = QSsd.ssd;
        QCase qCase = QCase.case$;
        QPower power = QPower.power;
        QKeyboard keyboard = QKeyboard.keyboard;
        QMouse mouse = QMouse.mouse;
        QMonitor monitor = QMonitor.monitor;

        BooleanBuilder builder = new BooleanBuilder();

        JPAQuery<ProductSearchResponseDTO> jpaQuery = queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
                product.productNo,product.productName,
                product.productCategory,product.productDescription,
                product.productThumbnail,product.productPrice,
                productReview.reviewGrade.avg().as("reviewGradeAvg"),productReview.reviewNo.count().as("reviewCount")))
                .from(product)
                .leftJoin(productReview).on(product.eq(productReview.productNo));

        switch (filterDTO.getCategory().toLowerCase()) {
            //where 에서 and or 명시 안하면 and 로 통일.
            //switch 문 을 안쓰고 해결할 수 있는 방법을 나중에 생각해보기..
            case "cpu":

                jpaQuery.leftJoin(cpu).on(product.eq(cpu.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(cpu.cpuCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(cpu.cpuGeneration.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(cpu.cpuModel.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }
                break;

            case "motherboard":

                jpaQuery.leftJoin(motherboard).on(product.eq(motherboard.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(motherboard.motherboardCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(motherboard.motherboardSocket.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(motherboard.motherboardChipset.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }
                break;

            case "vga":

                jpaQuery.leftJoin(vga).on(product.eq(vga.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(vga.vgaCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(vga.vgaChipset.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(vga.vgaSeries.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }
                break;

            case "memory":

                jpaQuery.leftJoin(memory).on(product.eq(memory.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(memory.memoryCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(memory.memorySize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                break;

            case "hdd":

                jpaQuery.leftJoin(hdd).on(product.eq(hdd.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(hdd.hddCompany.eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(hdd.hddSize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }

                break;

            case"ssd":

                jpaQuery.leftJoin(ssd).on(product.eq(ssd.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(ssd.ssdCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(ssd.ssdSize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                break;

            case "case":

                jpaQuery.leftJoin(qCase).on(product.eq(qCase.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(qCase.caseCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(qCase.caseSize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                break;

            case "power":

                jpaQuery.leftJoin(power).on(product.eq(power.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(power.powerCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(power.powerWatt.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                break;

            case "keyboard":

                jpaQuery.leftJoin(keyboard).on(product.eq(keyboard.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(keyboard.keyboardCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(keyboard.keyboardConnection.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(keyboard.keyboardContact.eq(filterDTO.getSelect3().toUpperCase()));
                }
                break;

            case "mouse":

                jpaQuery.leftJoin(mouse).on(product.eq(mouse.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(mouse.mouseCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(mouse.mouseConnection.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                break;
            case "monitor":
                jpaQuery.leftJoin(monitor).on(product.eq(monitor.productNo));
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(monitor.monitorCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(monitor.monitorPanel.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(monitor.monitorSize.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }
                break;
        }
        //builder where 넣기 + groupBy
        jpaQuery.where(builder)
                .groupBy(product.productNo,product.productName,
                        product.productCategory,product.productDescription,
                        product.productThumbnail,product.productPrice);

        switch (order.toLowerCase()) {
            case "popular" :
                jpaQuery.orderBy(productReview.reviewGrade.avg().desc());
                break;
            case "pricedesc":
                jpaQuery.orderBy(product.productPrice.desc());
                break;
            case "priceasc" :
                jpaQuery.orderBy(product.productPrice.asc());
                break;
        }

        jpaQuery.offset((page -1 ) * 10).limit(10);

        List<ProductSearchResponseDTO> responseDTOList = jpaQuery.fetch();

        return responseDTOList;
    }

    @Override
    public int countByFilterSearch(ProductFilterSearchRequestDTO filterDTO) {

        QCpu cpu = QCpu.cpu;
        QMotherboard motherboard = QMotherboard.motherboard;
        QVga vga = QVga.vga;
        QMemory memory = QMemory.memory;
        QHdd hdd = QHdd.hdd;
        QSsd ssd = QSsd.ssd;
        QCase qCase = QCase.case$;
        QPower power = QPower.power;
        QKeyboard keyboard = QKeyboard.keyboard;
        QMouse mouse = QMouse.mouse;
        QMonitor monitor = QMonitor.monitor;

        BooleanBuilder builder = new BooleanBuilder();

        switch (filterDTO.getCategory().toLowerCase()) {
            //where 에서 and or 명시 안하면 and 로 통일.
            //switch 문 을 안쓰고 해결할 수 있는 방법을 나중에 생각해보기..
            case "cpu":


                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(cpu.cpuCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(cpu.cpuGeneration.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(cpu.cpuModel.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }

                return (int) queryFactory.select(cpu.count()).from(cpu)
                        .where(builder).fetchCount();

            case "motherboard":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(motherboard.motherboardCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(motherboard.motherboardSocket.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(motherboard.motherboardChipset.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }
                return (int) queryFactory.select(motherboard.count()).from(motherboard)
                        .where(builder).fetchCount();

            case "vga":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(vga.vgaCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(vga.vgaChipset.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(vga.vgaSeries.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }

                return (int) queryFactory.select(vga.count()).from(vga)
                        .where(builder).fetchCount();

            case "memory":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(memory.memoryCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(memory.memorySize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                return (int) queryFactory.select(memory.count()).from(memory)
                        .where(builder).fetchCount();

            case "hdd":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(hdd.hddCompany.eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(hdd.hddSize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                return (int) queryFactory.select(hdd.count()).from(hdd)
                        .where(builder).fetchCount();

            case"ssd":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(ssd.ssdCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(ssd.ssdSize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                return (int) queryFactory.select(ssd.count())
                        .where(builder).fetchCount();

            case "case":
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(qCase.caseCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(qCase.caseSize.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                return (int) queryFactory.select(qCase.count()).from(qCase)
                        .where(builder).fetchCount();

            case "power":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(power.powerCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(power.powerWatt.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                return (int) queryFactory.select(power.count()).from(power)
                        .where(builder).fetchCount();

            case "keyboard":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(keyboard.keyboardCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(keyboard.keyboardConnection.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(keyboard.keyboardContact.eq(filterDTO.getSelect3().toUpperCase()));
                }
                return (int) queryFactory.select(keyboard.count()).from(keyboard)
                        .where(builder).fetchCount();

            case "mouse":

                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(mouse.mouseCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(mouse.mouseConnection.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }

                return (int) queryFactory.select(mouse.count()).from(mouse)
                        .where(builder).fetchCount();

            case "monitor":
                if(!StringUtils.isEmpty(filterDTO.getSelect1())) {
                    builder.and(monitor.monitorCompany.upper().eq(filterDTO.getSelect1().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect2())) {
                    builder.and(monitor.monitorPanel.upper().eq(filterDTO.getSelect2().toUpperCase()));
                }
                if(!StringUtils.isEmpty(filterDTO.getSelect3())) {
                    builder.and(monitor.monitorSize.upper().eq(filterDTO.getSelect3().toUpperCase()));
                }
                return (int) queryFactory.select(monitor.count()).from(monitor)
                        .where(builder).fetchCount();
        }

        return 0;
    }
}
