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
import com.memoryboost.domain.vo.product.response.ProductDetailResponseVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
    public List<ProductSearchResponseDTO> productSearch(String[] searchArr, String order, int page) {

        QProduct product = QProduct.product;
        QProductReview productReview = QProductReview.productReview;

        BooleanBuilder builder = new BooleanBuilder();
        for (String name : searchArr) {
            builder.and(product.productName.contains(name));
        }
        //동적 order by
        JPAQuery<ProductSearchResponseDTO> jpaQuery = queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
                product.productNo, product.productName,
                product.productCategory, product.productDescription,
                product.productThumbnail, product.productPrice,
                productReview.reviewGrade.avg().as("reviewGradeAvg"), productReview.reviewNo.count().as("reviewCount")))
                .from(product)
                .leftJoin(productReview).on(product.eq(productReview.productNo))
                .where(builder)
                .groupBy(product.productNo, product.productName,
                        product.productCategory, product.productDescription,
                        product.productThumbnail, product.productPrice);
        //order 에 따라서 order by 를 다르게
        switch (order.toLowerCase()) {
            case "popular":
                jpaQuery.orderBy(productReview.reviewGrade.avg().desc());
                break;
            case "pricedesc":
                jpaQuery.orderBy(product.productPrice.desc());
                break;
            case "priceasc":
                jpaQuery.orderBy(product.productPrice.asc());
                break;
        }

        jpaQuery.offset((page - 1) * 10).limit(10);

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

        for (String name : searchArr) {
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
    public List<ProductSearchResponseDTO> productFilterSearch(ProductFilterSearchRequestDTO filterDTO, String order, int page) {
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
                product.productNo, product.productName,
                product.productCategory, product.productDescription,
                product.productThumbnail, product.productPrice,
                productReview.reviewGrade.avg().as("reviewGradeAvg"), productReview.reviewNo.count().as("reviewCount")))
                .from(product)
                .leftJoin(productReview).on(product.eq(productReview.productNo));

        switch (filterDTO.getCategory().toLowerCase()) {
            //where 에서 and or 명시 안하면 and 로 통일.
            //switch 문 을 안쓰고 해결할 수 있는 방법을 나중에 생각해보기..
            case "cpu":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(1));
                }

                jpaQuery.leftJoin(cpu).on(product.eq(cpu.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(cpu.cpuCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(cpu.cpuGeneration.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(cpu.cpuModel.upper().eq(select.toUpperCase()));
                    }
                }

                break;

            case "motherboard":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(2));
                }

                jpaQuery.leftJoin(motherboard).on(product.eq(motherboard.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(motherboard.motherboardCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(motherboard.motherboardSocket.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(motherboard.motherboardChipset.upper().eq(select.toUpperCase()));
                    }
                }
                break;

            case "vga":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(3));
                }

                jpaQuery.leftJoin(vga).on(product.eq(vga.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(vga.vgaCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(vga.vgaChipset.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(vga.vgaSeries.upper().eq(select.toUpperCase()));
                    }
                }

                break;

            case "memory":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(4));
                }

                jpaQuery.leftJoin(memory).on(product.eq(memory.productNo));

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(memory.memoryCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(memory.memorySize.upper().eq(select.toUpperCase()));
                    }
                }

                break;

            case "hdd":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(5));
                }

                jpaQuery.leftJoin(hdd).on(product.eq(hdd.productNo));

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(hdd.hddCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(hdd.hddSize.upper().eq(select.toUpperCase()));
                    }
                }
                break;

            case "ssd":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(6));
                }

                jpaQuery.leftJoin(ssd).on(product.eq(ssd.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(ssd.ssdCompany.upper().eq(select.toUpperCase()));
                    }
                }

                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(ssd.ssdSize.upper().eq(select.toUpperCase()));
                    }
                }
                break;

            case "case":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(7));
                }

                jpaQuery.leftJoin(qCase).on(product.eq(qCase.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(qCase.caseCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(qCase.caseSize.upper().eq(select.toUpperCase()));
                    }
                }
                break;

            case "power":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(8));
                }

                jpaQuery.leftJoin(power).on(product.eq(power.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(power.powerCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(power.powerWatt.upper().eq(select.toUpperCase()));
                    }
                }
                break;

            case "keyboard":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(9));
                }

                jpaQuery.leftJoin(keyboard).on(product.eq(keyboard.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(keyboard.keyboardCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(keyboard.keyboardConnection.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(keyboard.keyboardContact.upper().eq(select.toUpperCase()));
                    }
                }
                break;

            case "mouse":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(10));
                }

                jpaQuery.leftJoin(mouse).on(product.eq(mouse.productNo));
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(mouse.mouseCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(mouse.mouseConnection.upper().eq(select.toUpperCase()));
                    }
                }

                break;
            case "monitor":
                if(filterDTO.nullCheck()) {
                    builder.and(product.productCategory.eq(11));
                }

                jpaQuery.leftJoin(monitor).on(product.eq(monitor.productNo));

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(monitor.monitorCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(monitor.monitorPanel.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(monitor.monitorSize.upper().eq(select.toUpperCase()));
                    }
                }
                break;
        }
        //builder where 넣기 + groupBy
        jpaQuery.where(builder)
                .groupBy(product.productNo, product.productName,
                        product.productCategory, product.productDescription,
                        product.productThumbnail, product.productPrice);

        switch (order.toLowerCase()) {
            case "popular":
                jpaQuery.orderBy(productReview.reviewGrade.avg().desc());
                break;
            case "pricedesc":
                jpaQuery.orderBy(product.productPrice.desc());
                break;
            case "priceasc":
                jpaQuery.orderBy(product.productPrice.asc());
                break;
        }

        jpaQuery.offset((page - 1) * 10).limit(10);

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

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(cpu.cpuCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(cpu.cpuGeneration.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(cpu.cpuModel.upper().eq(select.toUpperCase()));
                    }
                }

                return (int) queryFactory.select(cpu.count()).from(cpu)
                        .where(builder).fetchCount();

            case "motherboard":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(motherboard.motherboardCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(motherboard.motherboardSocket.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(motherboard.motherboardChipset.upper().eq(select.toUpperCase()));
                    }
                }
                return (int) queryFactory.select(motherboard.count()).from(motherboard)
                        .where(builder).fetchCount();

            case "vga":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(vga.vgaCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(vga.vgaChipset.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(vga.vgaSeries.upper().eq(select.toUpperCase()));
                    }
                }

                return (int) queryFactory.select(vga.count()).from(vga)
                        .where(builder).fetchCount();

            case "memory":

                for(String select : filterDTO.getSelect1()) {
                    builder.or(memory.memoryCompany.upper().eq(select.toUpperCase()));
                }
                for(String select : filterDTO.getSelect2()) {
                    builder.or(memory.memorySize.upper().eq(select.toUpperCase()));
                }
                return (int) queryFactory.select(memory.count()).from(memory)
                        .where(builder).fetchCount();

            case "hdd":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(hdd.hddCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(hdd.hddSize.upper().eq(select.toUpperCase()));
                    }
                }
                return (int) queryFactory.select(hdd.count()).from(hdd)
                        .where(builder).fetchCount();

            case "ssd":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(ssd.ssdCompany.upper().eq(select.toUpperCase()));
                    }
                }

                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(ssd.ssdSize.upper().eq(select.toUpperCase()));
                    }
                }
                return (int) queryFactory.select(ssd.count())
                        .where(builder).fetchCount();

            case "case":
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(qCase.caseCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(qCase.caseSize.upper().eq(select.toUpperCase()));
                    }
                }
                return (int) queryFactory.select(qCase.count()).from(qCase)
                        .where(builder).fetchCount();

            case "power":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(power.powerCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(power.powerWatt.upper().eq(select.toUpperCase()));
                    }
                }

                return (int) queryFactory.select(power.count()).from(power)
                        .where(builder).fetchCount();

            case "keyboard":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(keyboard.keyboardCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(keyboard.keyboardConnection.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(keyboard.keyboardContact.upper().eq(select.toUpperCase()));
                    }
                }
                return (int) queryFactory.select(keyboard.count()).from(keyboard)
                        .where(builder).fetchCount();

            case "mouse":

                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(mouse.mouseCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(mouse.mouseConnection.upper().eq(select.toUpperCase()));
                    }
                }

                return (int) queryFactory.select(mouse.count()).from(mouse)
                        .where(builder).fetchCount();

            case "monitor":
                if(filterDTO.getSelect1() != null) {
                    for(String select : filterDTO.getSelect1()) {
                        builder.or(monitor.monitorCompany.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect2() != null) {
                    for(String select : filterDTO.getSelect2()) {
                        builder.or(monitor.monitorPanel.upper().eq(select.toUpperCase()));
                    }
                }
                if(filterDTO.getSelect3() != null) {
                    for(String select : filterDTO.getSelect3()) {
                        builder.or(monitor.monitorSize.upper().eq(select.toUpperCase()));
                    }
                }
                return (int) queryFactory.select(monitor.count()).from(monitor)
                        .where(builder).fetchCount();
        }

        return 0;
    }

    @Override
    public ProductDetailResponseVO productDetail(Product productEntity) {

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        QProductReview productReview = QProductReview.productReview;

        ProductDetailResponseVO productDetailResponseVO = queryFactory.select(Projections.fields(ProductDetailResponseVO.class,product.productNo, product.productName,
                product.productCategory, product.productDescription, product.productThumbnail,
                product.productPrice)).from(product).where(product.eq(productEntity))
                .fetchOne();

        productDetailResponseVO.setProductImagePath(queryFactory.select(productImage.productImagePath)
                .from(productImage).where(productImage.productNo.eq(productEntity)).fetch());

        return productDetailResponseVO;

    }
}