package kr.gagaotalk.core;

import java.nio.charset.StandardCharsets;

public enum Action {
    signIn("signIn"),
    signOut("signOut"),
    findID("findID"),
    findPW("findPW"),
    upPW("upPW"),
    getFrens("getFrens"),
    getUser("getUser"),
    getCtRms("getCtRms"),
    upUsrInf("upUsrInf"),
    mkCtRm("mkCtRm"),
    addCtRm("addCtRm"),
    lvCtRm("lvCtRm"),
    sendMsg("sendMsg"),
    rcvMsg("rcvMsg"),
    invChtRm("invChtRm"),
    downFile("downFile"),
    uplFile("uplFile"),
    ruOnline("ruOnline"),
    hi("hi"),
    bye("bye");

    private final String code;
    Action(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
       return code;
    }

    public byte[] getBytes() {
        return toString().getBytes(StandardCharsets.UTF_8);
    }
}
