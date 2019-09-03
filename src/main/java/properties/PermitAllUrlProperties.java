package properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import lombok.Data;

@Data
public class PermitAllUrlProperties {
	
	private static List<Pattern> permitallurlPattern;
	private List<url> permitall;
	
	public String[] getPermitallPatterns() {
		List<String> urls = new ArrayList<>();
		Iterator<url> iterator = permitall.iterator();
		while(iterator.hasNext()) {
			urls.add(iterator.next().getPattern());
		}
		
		return urls.toArray(new String[0]);
	}
	
	public static class url {
		private String pattern;
		
		public String getPattern() {
			return pattern;
		}
		
		public void setPattern(String pattern) {
            this.pattern = pattern;
        }
	}
	
	@PostConstruct
	public void init() {
		if(permitall != null && permitall.size() > 0) {
			this.permitallurlPattern = new ArrayList();
			Iterator<url> iterator = permitall.iterator();
			while(iterator.hasNext()) {
				String currentUrl = iterator.next().getPattern().replaceAll("\\*\\*", "(.*?)");
				Pattern currentPattern = Pattern.compile(currentUrl, Pattern.CASE_INSENSITIVE);
				permitallurlPattern.add(currentPattern);
			}
		}
	}
	
	public boolean isPermitAllUrl(String url) {
		for(Pattern pattern : permitallurlPattern) {
			if(pattern.matcher(url).find()) {
				return true;
			}
		}
		return false;
	}

}
