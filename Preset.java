package com.estock;

public class Preset
{
    public String name, asins;
    public int price;

    public Preset(){}

    public Preset(String name, String asins, int price)
    {
        this.name = name;
        this.asins = asins;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsins() {
        return asins;
    }

    public void setAsins(String asins) {
        this.asins = asins;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /*SIXTY("3060","B08WPJ5P4R,B08WT47L8B,B08WHJPBFX,B08W8DGK3X,B08WGTL4CW,B08X4V8N5Q,B08WM28PVH,B08WPRMVWB,B08X4VJ6SB,B08P2H5LW2",700),
    SIXTYTI("3060ti","B08NYPLXPJ,B08NW5HNYW,B08P2HBBLX,B08P3V572B,B083Z5P6TX,B083Z7TR8Z,B08P3XJLJJ,B08P2D1JZZ,B08NW693LG",750),
    SEVENTY("3070","B08HBF5L3K,B08HBJB7YD,B08KWN2LZG,B08LF1CWT2,B08L8LG4M3,B08L8L9TCZ,B08L8L71SM,B08M14Y3C7,B08M13DXSZ,B08KY266MG,B08KWLMZV4,B08L8HPKR6,B08MT6B58K,B08L8JNTXQ,B08L8KC1J7,B08LW46GH2,B08KWPDXJZ,B08LF32LJ6,B08M4YFNX2,B08R15HVJ3",950),
    EIGHTY("3080","B08HR5SXPS,B08QW8BKDV,B08HR3Y5GQ,B08J6F174Z,B08HJTH61J,B08HHDP9DW,B08HH5WF97,B08HVV2P4Z,B08HR6FMF3,B08HR55YB5,B08HR7SV3M,B083GSKZSW,B08KTWTMK5",1150),
    NINETY("3090","B08J5F3G18,B08J61SS5R,B08HJQ182D,B08HJRF2CN,B08J6GMWCQ,B08HR9D2JS,B08HJGNJ81,B08HRBW6VB,B08KTWVHQP,B08R133PYZ",2000),
    PS5("PS5","B08FC5L3RG",570);*/

}
