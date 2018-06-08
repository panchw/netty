package netty.c2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    
    private final int port;
    
    public EchoServer(int port){
        this.port = port;
    }
    
    public static void main(String[] args) throws Exception {
        new EchoServer(8080).start();
    }
    
    public void start() throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);
                    }
                });
            ChannelFuture future = b.bind().sync();
            future.channel().closeFuture().sync();
        }finally{
            group.shutdownGracefully();
        }
    }
}
