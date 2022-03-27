URL Class

- The URL class is the simplest way to locate and retrieve data from the network.

- do not need to worry about the details of the protocol being used, the format of the data being retrieved, or how to       communicate with   the server;
 
-simply tell Java the URL and it gets the data for you.

- The java.net.URL class is an abstraction of a Uniform Resource Locator 

public final class URL extends Object implements Serializable

Constructors

 - The simplest URL constructor just takes an absolute URL in string form as its single argument:
        
        public URL(String url) throws MalformedURLException

Example : URL Constructor
try {
  URL u = new URL("http://www.audubon.org/");
}
catch (MalformedURLException ex)  {
  System.err.println(ex);
}
.............................................................................................................
Example : Get  HTML form UCP Faculty Page from ucp.edu.pk using URL

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
class Test {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.ucp.edu.pk/fit/faculty_staff");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
               System.out.println(inputLine);
        in.close();
    }
}
........................................................................................
Note: Proxy Setting required for 
        System.setProperty("http.proxyHost", "192.168.1.10");
        System.setProperty("http.proxyPort", "8080");
.........................................................................................
Example : Get  HTML form UCP Faculty Page from ucp.edu.pk using URL
                 (Same Example with Proxy Setting)
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
class Test {
    public static void main(String[] args) throws Exception {
        System.setProperty("http.proxyHost", "192.168.1.10");
        System.setProperty("http.proxyPort", "8080");
        URL url = new URL("http://www.ucp.edu.pk/fit/faculty_staff");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
               System.out.println(inputLine);
        in.close();
    }
}
..........................................................................................
Example :Detecting Proxy server IP and Port on your network if any 

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
 class Test  {
  public static void main(String[] args) throws Exception {
    System.setProperty("java.net.useSystemProxies", "true");
    List l = ProxySelector.getDefault().select(new URI("http://www.yahoo.com/"));

    for (Iterator iter = l.iterator(); iter.hasNext();) {
      Proxy proxy = (Proxy) iter.next();
      System.out.println("proxy hostname : " + proxy.type());
      InetSocketAddress addr = (InetSocketAddress) proxy.address();
      if (addr == null) {
        System.out.println("No Proxy");
      } else {
        System.out.println("proxy hostname : " + addr.getHostName());
        System.out.println("proxy port : " + addr.getPort());
      }
    }
  }
}
.......................................................................................
Example :Get  Email Addresses of  UCP Faculty  from ucp.edu.pk using URL
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.regex.*;
class Test {
    public static void main(String[] args) throws Exception {
        System.setProperty("http.proxyHost", "192.168.1.10");
        System.setProperty("http.proxyPort", "8080");
        URL url = new URL("http://www.ucp.edu.pk/fit/faculty_staff");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
               nameFound(inputLine);
        in.close();
    }
static void found(String p ){
     Pattern pattern =
      Pattern.compile("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
      Matcher matcher = pattern.matcher(p);
      if (matcher.find()) {
              System.out.println(p.substring( matcher.start(), matcher.end()));
      }
      
}
static void nameFound(String p ){
     Pattern pattern =
      Pattern.compile("(alt=)\"[a-zA-Z ]+\"");
      Matcher matcher = pattern.matcher(p);
      while (matcher.find()) {
System.out.println(p.substring( matcher.start()+5, matcher.end()-1));
      }      
}
}
.........................................................................................
Example  File Download from URL

import java.net.*;
import java.io.*;
class DownLoadFile{
  public static void main(String[] args){
        System.setProperty("http.proxyHost", "192.168.1.10");
        System.setProperty("http.proxyPort", "8080");
     try {
        /*
         * Get a connection to the URL and start up
         * a buffered reader.
         */
        long startTime = System.currentTimeMillis();  
        System.out.println("Connecting to Mura site...\n");  
        URL url = new URL("http://www.getmura.com/currentversion/");
        url.openConnection();
        InputStream reader = url.openStream();  
        /*
         * Setup a buffered file writer to write
         * out what we read from the website.

         */
        FileOutputStream writer = new FileOutputStream("C:/mura-newest.zip");
        byte[] buffer = new byte[153600]; // Buffer for 150K blocks at a time
        int totalBytesRead = 0;
        int bytesRead = 0;
        System.out.println("Reading ZIP file 150KB blocks at a time.\n");  

        while ((bytesRead = reader.read(buffer)) > 0){  
           writer.write(buffer, 0, bytesRead);
           buffer = new byte[153600];
           totalBytesRead += bytesRead;
        }  
        long endTime = System.currentTimeMillis();  

        System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + 
               (new Long(endTime - startTime).toString())+ " millseconds).\n");
        writer.close();
        reader.close();
     }catch (MalformedURLException e){
        e.printStackTrace();
     }
     catch (IOException e){
        e.printStackTrace();
     }
  }
}
.................................................................................................
Test which Protocol is Supported by Java virtual Machine  

construct a URL object for each of 14 protocols (8 standard protocols, 3 custom protocols 
for various Java APIs, and 4 undocumented protocols used internally by HotJava). 
If the constructor succeeds, the protocol is supported. Otherwise, a MalformedURLException is 
thrown and hence protocol is not supported.

.................................................................................................
Example determining which protocols a virtual machine supports.

import java.net.*;
class Test {
  public static void main(String[] args) {
    // hypertext transfer protocol
    testProtocol("http://www.adc.org");  
    // secure http

    testProtocol("https://www.amazon.com/exec/obidos/order2/"); 
    // file transfer protocol

    testProtocol("ftp://metalab.unc.edu/pub/languages/java/javafaq/");

    // Simple Mail Transfer Protocol 

    testProtocol("mailto:elharo@metalab.unc.edu");

    // telnet 

    testProtocol("telnet://dibner.poly.edu/");

    // local file access

    testProtocol("file:///etc/passwd");

    // gopher 

    testProtocol("gopher://gopher.anc.org.za/");


    // Lightweight Directory Access Protocol

    testProtocol(
     "ldap://ldap.itd.umich.edu/o=University%20of%20Michigan,c=US?postalAddress");

    // JAR

    testProtocol(

     "jar:http://cafeaulait.org/books/javaio/ioexamples/javaio.jar!"

         +"/com/macfaq/io/StreamCopier.class");

    // NFS, Network File System

    testProtocol("nfs://utopia.poly.edu/usr/tmp/");

    // a custom protocol for JDBC

    testProtocol("jdbc:mysql://luna.metalab.unc.edu:3306/NEWS");

    // rmi, a custom protocol for remote method invocation

    testProtocol("rmi://metalab.unc.edu/RenderEngine");

    // custom protocols for HotJava

    testProtocol("doc:/UsersGuide/release.html");

    testProtocol("netdoc:/UsersGuide/release.html");

    testProtocol("systemresource://www.adc.org/+/index.html");

    testProtocol("verbatim:http://www.adc.org/");
  

  }
  private static void testProtocol(String url) {

    try {  

      URL u = new URL(url);

      System.out.println(u.getProtocol( ) + " is supported");

    }

    catch (MalformedURLException ex) {

      String protocol = url.substring(0, url.indexOf(':'));

      System.out.println(protocol + " is not supported");

    }

  } 

}

OUtput (on JDK1.7.0)


http is supported
https is supported
ftp is supported
mailto is supported
telnet is not supported
file is supported
gopher is supported
ldap is not supported
jar is supported
nfs is not supported
jdbc is not supported
rmi is not supported
doc is not supported
netdoc is supported
systemresource is not supported
verbatim is not supported
......................................................................................
Splitting a URL into Pieces

URLs are composed of five pieces:

1- The scheme, also known as the protocol

2- The authority

3- The path

4- The fragment identifier, also known as the section or ref

5- The query string

-Read-only access to these parts of a URL is provided by five public methods: getFile( ), getHost(),
 getPort( ), getProtocol( ), and getRef( ).
- Java 1.3 adds four more methods: getQuery( ), getPath( ), getUserInfo( ), and getAuthority( ).



