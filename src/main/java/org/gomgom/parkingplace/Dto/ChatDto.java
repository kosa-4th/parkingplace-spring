package org.gomgom.parkingplace.Dto;

import lombok.AllArgsConstructor;

public class ChatDto {

    //message관련 request 데이터를 받는 클래스
    @AllArgsConstructor
    public static class ChattingDto {
        private String message;
        private String writer;
        private Long memberNo;
        private Long roomNo;
    }
}
