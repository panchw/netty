package netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    
    private final int port;

    public EchoClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }
    
    public void start() throws InterruptedException{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                    
                });
            ChannelFuture f = b.connect().sync();
            Thread.sleep(2000);
            f.channel().closeFuture().sync();
        }finally{
            group.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        new EchoClient("localhost", 8080).start();
    }
}
