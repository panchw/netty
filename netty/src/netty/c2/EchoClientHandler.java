package netty.c2;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    Logger log = Logger.getLogger(this.getClass().getName());
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
            throws Exception {
        log.info("client recevied :" + msg.toString(CharsetUtil.UTF_8));
        
    }

    
    
}
