package com.zjl._6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @description
 * @date 2023/2/21 23:51
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + "服务端读到数据 -> " + byteBuf.toString(Charset.forName("UTF-8")));
        // 回复时间到客户端
    }
}
