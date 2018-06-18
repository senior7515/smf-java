package example.demo;

import com.google.flatbuffers.FlatBufferBuilder;
import core.SmfClient;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.Arrays;
import java.nio.ByteOrder;

public class DemoApp {
  public static void main(String... args) throws InterruptedException {

    final SmfClient smfClient = new SmfClient("127.0.0.1", 7000);

    final SmfStorageClient smfStorageClient = new SmfStorageClient(smfClient);

    // construct get request.
    FlatBufferBuilder requestBuilder = new FlatBufferBuilder(0);
    int requestPosition = requestBuilder.createString("GET /something/");

    demo.Request.startRequest(requestBuilder);
    demo.Request.addName(requestBuilder, requestPosition);
    int root = demo.Request.endRequest(requestBuilder);
    requestBuilder.finish(root);

    byte[] body = requestBuilder.sizedByteArray();

    // of course, this will be removed as well
    final CountDownLatch latch = new CountDownLatch(1);

    smfStorageClient.get(
        body,
        response -> {
          byte[] hdrbytes = new byte[16];
          Arrays.fill(hdrbytes, (byte)0);
          response.readBytes(hdrbytes);
          ByteBuffer bb = ByteBuffer.wrap(hdrbytes);
          bb.order(ByteOrder.LITTLE_ENDIAN);
          smf.Header hdr = new smf.Header();
          hdr.__init(0, bb);
          System.out.println(
              "Received Response details =============================================");

          System.out.println("compression : " + (byte)hdr.compression());
          System.out.println("bitflags : " + (byte)hdr.bitflags());
          System.out.println("session : " + (short)hdr.session());
          System.out.println("size : " + (int)hdr.size());
          System.out.println("checksum : " + (int)hdr.checksum());
          System.out.println("meta : " + (int)hdr.meta());

          System.out.println();

          int bytesToRead      = (int) hdr.size();
          byte[] responseBytes = new byte[bytesToRead];
          response.readBytes(responseBytes);

          System.out.println(
              "Response dump (hex) = = = = = = = = = = = = = = = = = = = = = = = = = =");

          for (int i = 0; i < responseBytes.length; i++) {
            if (i % 10 == 0) {
              System.out.println();
            }

            System.out.print(String.format("     %02X", responseBytes[i]));
          }
          System.out.println(
              "\n= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");
          System.out.println(
              "\n========================================================================");

          latch.countDown();
        });

    // await response
    latch.await();

    // close client
    smfClient.closeGracefully();
  }
}
