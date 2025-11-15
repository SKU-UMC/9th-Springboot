package com.example.umc_9th_springboot.domain.review.enums;

/** 별점대 필터 */
public enum StarScore {

    FIVE(5), FOUR(4), THREE(3), TWO(2), ONE(1);

    public final int score;

    StarScore(int score) { this.score = score; }

    public double min() { return score; } //min 정의
    public double max() { return score == 5 ? 5.0 : score + 1.0; } //max 정의

    public static StarScore from(Integer v) {
        if (v == null) return null;
        return switch (v) {
            case 5 -> FIVE;
            case 4 -> FOUR;
            case 3 -> THREE;
            case 2 -> TWO;
            case 1 -> ONE;
            default -> null;
        };
    }
}
