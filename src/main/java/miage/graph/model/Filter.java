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
						if (linkProperty.getValue().equals("Since")) {
							Since since = (Since) linkProperty;
							if (since.getdate().equals(filterArray[1])) {
								match = true;
								countFilters++;
							}
						}
						break;
					case "shared":
						if (linkProperty.getValue().equals("Shared")) {
							int countShared = 0;
							Shared shared = (Shared) linkProperty;
							String[] sharedArray = filterArray[1].split(",");
							for (String sharedString : sharedArray) {
								if (shared.getShared().contains(sharedString)) {
									countShared++;
								}
							}
							if (countShared == sharedArray.length) {
								match = true;
							}
							countFilters++;
						}
						break;
					case "hired":
						if (linkProperty.getValue().equals("Hired")) {
							Hired hired = (Hired) linkProperty;
							if (hired.getDate().equals(filterArray[1])) {
								match = true;
								countFilters++;
							}
						}
						break;
					case "role":
						if (linkProperty.getValue().equals("Role")) {
							Role role = (Role) linkProperty;
							if (role.getRole().equals(filterArray[1])) {
								match = true;
								countFilters++;
							}
						}
						break;
					}
				}
			}
		}
		if (countFilters == filters.size()) {
			return match;
		}
		return false;

	}
}
