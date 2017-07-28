import java.io.*; 
import java.net.*; 
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class Files implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private static String destinationDirectory;
    private String sourceDirectory;
    private static String filename;
    private long fileSize;
    private static byte[] fileData;
    private static String status;

    public  String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public  String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public  String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
	
}

public class UServer {
    

	private static Files Files=null;

    public static void createAndWriteFile()
    {
        String outputFile = Files.getDestinationDirectory() + Files.getFilename();
        if (!new File(Files.getDestinationDirectory()).exists()) {

              
            new File(Files.getDestinationDirectory()).mkdirs();
        }
        File dstFile = new File(outputFile);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(Files.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Output file : " + outputFile + " is successfully saved ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    	try {
			System.out.println("Server  initialized\n");
			DatagramSocket serverSocket = new DatagramSocket(6666);
            byte[] incomingData = new byte[1024 * 1000 * 50];
            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				long start = System.currentTimeMillis();
				serverSocket.receive(incomingPacket);
				long now = System.currentTimeMillis();
				long ttime = now - start;
                                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                 Files = (Files) is.readObject();
                if (Files.getStatus().equalsIgnoreCase("Error")) {
                    System.out.println("No Input");
                    System.exit(0);
                }
                createAndWriteFile();   // writing the file to hard disk
				System.out.println("Recieved file in : "+ttime+" nanosec");
				InetAddress IPAddress = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
				
                String reply = "File has been recieved";
                byte[] replyBytea = reply.getBytes();
                DatagramPacket replyPacket =
                        new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);
                serverSocket.send(replyPacket);
                Thread.sleep(3000);
                
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } 
    }
}
