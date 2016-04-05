/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package z.pub.test.jndi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.junit.Test;

/**
 * 描述
 * 
 * @author Mars zhang
 * @created 2016年3月4日 下午4:01:19
 */
public class JNDITest {

    /**
     * 
     * 描述
     * 
     * @author Mars zhang
     * @created 2016年3月4日 下午4:02:13
     */
    @Test
    public void systemout() { 
        try {
            // 1、构建参数ApacheDs.properties
            InputStream fileInputStream = JNDITest.class.getClassLoader().getResourceAsStream("ApacheDs.properties");// new
                                                                                                                     // FileInputStream("ApacheDs.properties");
            Properties apacheDsProperties = new Properties();
            apacheDsProperties.load(fileInputStream);
            apacheDsProperties.setProperty("java.naming.security.credentials", "secret");
            // 2、创建上下文
            DirContext ctx = new InitialDirContext(apacheDsProperties);
            // 3、绑定参数
            // ctx.addToEnvironment("cn=preferences,uid=alice,ou=users", new
            // MessagingPreferences());

            // 插入
            Person person = new Person();
            person.setName("zhang_lin4");
            person.setAddress("泉州");
            person.setPassword("888888");
            person.setP(new MessagingPreferences());
            insert(person, ctx);
            search(person, ctx);
//            edit(person, ctx);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean insert(Person person, DirContext dctx) {
        try {

            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("uid", person.getName()));
            matchAttrs.put(new BasicAttribute("cn", person.getName()));
            matchAttrs.put(new BasicAttribute("street", person.getAddress()));
            matchAttrs.put(new BasicAttribute("objectclass", person.getP()));
            matchAttrs.put(new BasicAttribute("sn", person.getName()));
            matchAttrs.put(new BasicAttribute("userpassword", person.getPassword()));
            matchAttrs.put(new BasicAttribute("objectclass", "top"));
            matchAttrs.put(new BasicAttribute("objectclass", "person"));
            matchAttrs.put(new BasicAttribute("objectclass", "organizationalPerson"));
            matchAttrs.put(new BasicAttribute("objectclass", "inetorgperson"));
            String name = "uid=" + person.getName() + ",ou=users,ou=system";
            InitialDirContext iniDirContext = (InitialDirContext) dctx;
            iniDirContext.bind(name, dctx, matchAttrs);

            System.out.println("success inserting " + person.getName());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean delete(Person person, DirContext ctx) {
        try {
            ctx.destroySubcontext("uid=" + person.getName() + ",ou=users,ou=system");

            System.out.println("success deleting " + person.getName());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean edit(Person person, DirContext ctx) {
        try {
            ModificationItem[] mods = new ModificationItem[2];
            Attribute mod0 = new BasicAttribute("street", person.getAddress());
            Attribute mod1 = new BasicAttribute("userpassword", person.getPassword());
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);

            ctx.modifyAttributes("uid=" + person.getName() + ",ou=users,ou=system", mods);

            System.out.println("success editing " + person.getName());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean search(Person person, DirContext ctx) {
        try {

            String base = "ou=users,ou=system";

            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "(&(objectclass=person)(uid=" + person.getName() + "))";

            NamingEnumeration results = ctx.search(base, filter, sc);

            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();

                Attribute attr = attrs.get("uid");
                if (attr != null)
                    System.out.println("record found " + attr.get());
                System.out.println(attrs.get("userpassword").get()); 
//                Binary passwordbinary=  (Binary)attrs.get("userpassword").get();
//                System.out.println("record found " +passwordbinary.getBytes() );
            }
            ctx.close();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    
/*    private String encryptLdapPassword(String algorithm, String _password) {
        String sEncrypted = _password;
        if ((_password != null) && (_password.length() > 0)) {
            boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
            boolean bSHA = algorithm.equalsIgnoreCase("SHA")
                    || algorithm.equalsIgnoreCase("SHA1")
                    || algorithm.equalsIgnoreCase("SHA-1");
            if (bSHA || bMD5) {
                String sAlgorithm = "MD5";
                if (bSHA) {
                    sAlgorithm = "SHA";
                }
                try {
                    MessageDigest md = MessageDigest.getInstance(sAlgorithm);
                    md.update(_password.getBytes("UTF-8"));
                    sEncrypted = "{" + sAlgorithm + "}" + (new BASE64Encoder()).encode(md.digest());
                } catch (Exception e) {
                    sEncrypted = null;
                    logger.error(e, e);
                }
            }
        }
        return sEncrypted;
    }*/

}
