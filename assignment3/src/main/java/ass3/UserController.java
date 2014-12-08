package ass3;

import java.net.URI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdResult;

/**
 * @author Varun User Controller class
 */
@RestController
public class UserController extends WebMvcConfigurerAdapter {

	EtcdClient client = new EtcdClient(URI.create("http://54.67.93.97:4001/"));

	@RequestMapping(value = "/api/v1/counter", method = RequestMethod.GET)
	public EtcdResult setAndGet() throws Exception {

		EtcdResult result = null;
		String key = "/010007907";
		System.out.println("Keyyyyyyy : "+key);
		String str = "";
		try {
			result = this.client.get(key);
			System.out.println("Existing Counter : "+result);
			str = Integer.toString(Integer.parseInt(result.node.value) + 1);
			System.out.println("String try Value --->>> "+str);
		} catch (Exception exp) {
			str = "1";
		} finally {
			try {
				EtcdResult result1 = this.client.set(key, str);
				result = this.client.get(key);
				System.out.println("New Counter : "+result);
			} catch(Exception expt) {
				System.out.println("*** ERROR *** on machine http://54.67.93.97:4001/ -> "+expt.getMessage());
			}
		}
		return result;
	}
}