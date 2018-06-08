package netty.c2;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
    
    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf in = (ByteBuf)msg;
        log.info("Server recevied: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(ctx);
    }
    
    

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
            .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    
}
