package net.vipmro.search.redis;

/**
 * 缓存时间常量
 *
 * @author fengxiangyang
 * @date 2018/12/3
 */
public interface CacheTime {
    int MINUTE_1 = 60 * 1;
    int MINUTE_5 = MINUTE_1 * 5;
    int MINUTE_10 = MINUTE_1 * 10;
    int MINUTE_15 = MINUTE_1 * 15;
    int MINUTE_30 = MINUTE_1 * 30;
    int HOUR_1 = MINUTE_1 * 60;
    int HOUR_2 = HOUR_1 * 2;
    int HOUR_3 = HOUR_1 * 3;
    int HOUR_5 = HOUR_1 * 5;
    int HOUR_12 = HOUR_1 * 12;
    int DAY_1 = HOUR_1 * 24;
    int DAY_2 = DAY_1 * 2;
    int DAY_3 = DAY_1 * 3;
    int DAY_5 = DAY_1 * 5;
    int DAY_15 = DAY_1 * 15;
    int WEEK_1 = DAY_1 * 7;
    int WEEK_2 = WEEK_1 * 2;
    int MONTH_1 = DAY_1 * 30;
    int MONTH_2 = MONTH_1 * 2;
    int MONTH_3 = MONTH_1 * 3;
}
