package com.imtotem.chatsound.util;

public class FilteringCommand {
    private final String[] a = new String[] {"say"}; // 모든 플레이어에게 메세지를 보내는 기능만 있는 명령어
    private final String[] t = new String[] {"msg", "tell", "w"}; // 특정 플레이어에게 메세지를 보내는 기능이 있는 명령어

    private final String[] aList = new String[a.length * 2];
    private final String[] tList = new String[t.length * 2];

    public FilteringCommand() {
        String m = "minecraft:";
        int lenA = a.length;
        for (int i = 0; i < lenA; i++ ) {
            aList[i] = a[i];
            aList[lenA +i] = m +a[i];
        }

        int lenT = t.length;
        for (int i = 0; i < lenT; i++ ) {
            tList[i] = t[i];
            tList[lenT + i] = m +t[i];
        }
    }

    public String[] getACommand() {
        return aList;
    }

    public String[] getTCommand() {
        return tList;
    }
}
