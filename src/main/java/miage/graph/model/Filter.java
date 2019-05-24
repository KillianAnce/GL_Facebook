package miage.graph.model;

import java.util.Set;

public class Filter {

	private Filter() {

	}

	public static boolean checkFilters(Link link, Set<String> filters) {
		boolean match = false;
		String[] filterArray = null;
		int countFilters = 0;
		for (LinkProperties linkProperty : link.getLinkProperties()) {
			for (String filter : filters) {
				if (linkProperty != null) {
					filterArray = filter.split("=");
					switch (filterArray[0]) {
					case "since":
						match = sinceFilter(linkProperty, filterArray[1]);
						countFilters += match ? 1 : 0;
						break;
					case "shared":
						String[] sharedArray = filterArray[1].split(",");
						match = (sharedFilter(linkProperty, sharedArray) == sharedArray.length); 
						countFilters += match ? 1 : 0;	
						break;
					case "hired":
						match = hiredFilter(linkProperty, filterArray[1]);
						countFilters += match ? 1 : 0;
						break;
					case "role":
						match = roleFilter(linkProperty, filterArray[1]);
						countFilters += match ? 1 : 0;
						break;
					default:
						// nothing
						break;
					}
				}
			}
		}
		return countFilters == filters.size();
	}

	public static int sharedFilter(LinkProperties property, String[] filter) {
		int countShared = 0;

		if (property.getValue().equals("Shared")) {
			Shared shared = (Shared) property;
			for (String sharedString : filter) {
				if (shared.getShared().contains(sharedString)) {
					countShared++;
				}
			}
		}
		return countShared;
	}

	public static boolean sinceFilter(LinkProperties property, String filter) {
		Since since = null;
		if (property.getValue().equals("Since")) {
			since = (Since) property;
			return since.getdate().equals(filter);
		}
		return false;
	}

	public static boolean roleFilter(LinkProperties property, String filter) {
		Role role = null;
		if (property.getValue().equals("Role")) {
			role = (Role) property;
			return role.getRole().equals(filter);
		}
		return false;

	}

	public static boolean hiredFilter(LinkProperties property, String filter) {
		Hired hired = null;
		if (property.getValue().equals("Hired")) {
			hired = (Hired) property;
			return hired.getDate().equals(filter);
		}
		return false;
	}

}
