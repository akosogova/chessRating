package com.chessrating.convertor;

import com.chessrating.config.GlobalConfig;
import com.chessrating.model.ChessData;
import com.chessrating.repository.entity.ChessDataEntity;

import java.util.Objects;

public class ChessDataConvertor {
    public static ChessData convertChessData(ChessDataEntity chessData) {
        return ChessData.builder()
                .rating(chessData.getRating())
                .build();
    }

    public static ChessDataEntity convertChessData(ChessData chessData) {
        return ChessDataEntity.builder()
                .rating(Objects.isNull(chessData) ? GlobalConfig.INITIAL_RATING : chessData.getRating())
                .build();
    }
}
