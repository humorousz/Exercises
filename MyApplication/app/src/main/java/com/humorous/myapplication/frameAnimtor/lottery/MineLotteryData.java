package com.humorous.myapplication.frameAnimtor.lottery;

/**
 * Created by zhangzhiquan on 2017/10/2.
 */

public class MineLotteryData {
    private int id;
    private long price;
    private int lottery;
    private int count;


    private int allLottery;

    public MineLotteryData() {

    }

    public MineLotteryData(int id, long price, int lottery, int count, int allLottery) {
        this.id = id;
        this.price = price;
        this.lottery = lottery;
        this.count = count;
        this.allLottery = allLottery;
    }

    public MineLotteryData(int id, long price, int count, int allLottery) {
        this.id = id;
        this.price = price;
        this.count = count;
        this.allLottery = allLottery;
        this.lottery = (int) (allLottery / price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getLottery() {
        return lottery;
    }

    public void setLottery(int lottery) {
        this.lottery = lottery;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAllLottery() {
        return allLottery;
    }

    public void setAllLottery(int allLottery) {
        this.allLottery = allLottery;
    }
}
