package com.memoryboost.domain.dto.product.request;

import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.detail.cpu.Cpu;
import com.memoryboost.domain.entity.product.detail.hdd.Hdd;
import com.memoryboost.domain.entity.product.detail.keyboard.Keyboard;
import com.memoryboost.domain.entity.product.detail.memory.Memory;
import com.memoryboost.domain.entity.product.detail.monitor.Monitor;
import com.memoryboost.domain.entity.product.detail.motherboard.Motherboard;
import com.memoryboost.domain.entity.product.detail.mouse.Mouse;
import com.memoryboost.domain.entity.product.detail.power.Power;
import com.memoryboost.domain.entity.product.detail.ssd.Ssd;
import com.memoryboost.domain.entity.product.detail.tbcase.Case;
import com.memoryboost.domain.entity.product.detail.vga.Vga;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailSaveRequestDTO {

    //CPU
    // 제조회사
    private String cpuCompany;
    // 세대
    private String cpuGeneration;
    // 모델명
    private String cpuModel;

    //Motherboard
    // 제조회사
    private String motherboardCompany;
    // 소켓
    private String motherboardSocket;
    // 칩셋종류
    private String motherboardChipset;

    //vga
    // 제조회사
    private String vgaCompany;
    // 칩셋
    private String vgaChipset;
    // 제품 시리즈
    private String vgaSeries;

    //memory
    // 제조회사
    private String memoryCompany;
    // 제품용량
    private String memorySize;

    //hdd
    // 제조회사
    private String hddCompany;
    // 하드디스크용량
    private String hddSize;

    //SSD
    // 제조회사
    private String ssdCompany;
    // 스스디용량
    private String ssdSize;

    //CASE
    // 제조회사
    private String caseCompany;
    // 케이스 규격
    private String caseSize;

    //power
    // 제조회사
    private String powerCompany;
    // 파워정격
    private String powerWatt;

    //keyboard
    // 제조회사
    private String keyboardCompany;
    // 연결방식
    private String keyboardConnection;
    // 접촉방식
    private String keyboardContact;

    //mouse
    // 제조회사
    private String mouseCompany;
    // 연결방식
    private Integer mouseConnection;

    //monitor
    // 제조회사
    private String monitorCompany;
    // 패널
    private String monitorPanel;
    // 사이즈
    private String monitorSize;

    public Cpu cpuEntity(Product product) {
        return Cpu.builder()
                .productNo(product)
                .cpuCompany(cpuCompany)
                .cpuGeneration(cpuGeneration)
                .cpuModel(cpuModel)
                .build();
    }

    public Motherboard motherboardEntity(Product product) {
        return Motherboard.builder()
                .productNo(product)
                .motherboardCompany(motherboardCompany)
                .motherboardSocket(motherboardSocket)
                .motherboardChipset(motherboardChipset)
                .build();
    }

    public Vga vgaEntity(Product product) {
        return Vga.builder()
                .productNo(product)
                .vgaCompany(vgaCompany)
                .vgaChipset(vgaChipset)
                .vgaSeries(vgaSeries)
                .build();
    }

    public Memory memoryEntity(Product product) {
        return Memory.builder()
                .productNo(product)
                .memoryCompany(memoryCompany)
                .memorySize(memorySize)
                .build();
    }

    public Hdd hddEntity(Product product) {
        return Hdd.builder()
                .productNo(product)
                .hddCompany(hddCompany)
                .hddSize(hddSize)
                .build();
    }

    public Ssd ssdEntity(Product product) {
        return Ssd.builder()
                .productNo(product)
                .ssdCompany(ssdCompany)
                .ssdSize(ssdSize)
                .build();
    }

    public Case caseEntity(Product product) {
        return Case.builder()
                .productNo(product)
                .caseCompany(caseCompany)
                .caseSize(caseSize)
                .build();
    }

    public Power powerEntity(Product product) {
        return Power.builder()
                .productNo(product)
                .powerCompany(powerCompany)
                .powerWatt(powerWatt)
                .build();
    }

    public Keyboard keyboardEntity(Product product) {
        return Keyboard.builder()
                .productNo(product)
                .keyboardCompany(keyboardCompany)
                .keyboardConnection(keyboardConnection)
                .keyboardContact(keyboardContact)
                .build();
    }

    public Mouse mouseEntity(Product product) {
        return Mouse.builder()
                .productNo(product)
                .mouseCompany(mouseCompany)
                .mouseConnection(mouseConnection)
                .build();
    }

    public Monitor monitorEntity(Product product) {
        return Monitor.builder()
                .productNo(product)
                .monitorCompany(monitorCompany)
                .monitorPanel(monitorPanel)
                .monitorSize(monitorSize)
                .build();
    }

}
