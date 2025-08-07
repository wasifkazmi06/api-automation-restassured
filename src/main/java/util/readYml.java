package util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pojos.neobank.fd.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class readYml
{
    public static Configuration readYamlFile(Class responseClass, String filepath) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(responseClass));
        // String path = System.getProperty(("user.dir")+/)
        InputStream inputStream = new FileInputStream(filepath);
        System.out.println(inputStream);

        Configuration configuration = yaml.load(inputStream);
       /* System.out.println(configuration.getDatasource());
        System.out.println((configuration.getDatasource().getUrl()));*/
        return configuration;

    }
}
