import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;


public class download {

    public static void main(String[]args) throws IOException {
    File ficheiro = new File("C:\\Users\\Diogo Casaca\\testeSubTFC\\teste1.zip");
    unzip(new URL("https://www.7-zip.org/a/7z1900-src.7z"),ficheiro.toPath());
    }
    public static void unzip(final URL url, final Path decryptTo) throws IOException {
        System.out.println("inicio");
            java.nio.channels.AsynchronousByteChannel u = (AsynchronousByteChannel) Channels.newChannel(url.openStream());
            try (ZipInputStream zipInputStream = new ZipInputStream(Channels.newInputStream(u))) {
                System.out.println(u);
                System.out.println("entrei try");
                System.out.println(zipInputStream.getNextEntry());
                for (ZipEntry entry = zipInputStream.getNextEntry(); entry != null; entry = zipInputStream.getNextEntry()) {
                    Path toPath = decryptTo.resolve(entry.getName());
                    System.out.println(toPath.getFileName());
                    if (entry.isDirectory()) {
                        System.out.println("tou no if");
                        Files.createDirectory(toPath);
                    } else try (FileChannel fileChannel = FileChannel.open(toPath, WRITE, CREATE/*, DELETE_ON_CLOSE*/)) {
                        System.out.println("tou no else");
                        fileChannel.transferFrom(Channels.newChannel(zipInputStream), 0, Long.MAX_VALUE);
                    }
                }
            }
        }

}
