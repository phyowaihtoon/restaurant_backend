package creatip.restaurant.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO that represents the menu (action items) returned to the client.
 */
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Version or ETag for the menu payload (useful for caching/invalidation) */
	private String version;

	/** Action items with effective permissions for the current user */
	private List<ActionItemDTO> items;

	/** Ordering / grouping information for the action items */
	private List<ActionItemOrderDTO> orders;
	/** Nested tree assembled on server for convenient rendering */
	private List<NestedMenuItemDTO> tree;

	public MenuDTO() {}

	public MenuDTO(String version, List<ActionItemDTO> items, List<ActionItemOrderDTO> orders, List<NestedMenuItemDTO> tree) {
		this.version = version;
		this.items = items;
		this.orders = orders;
		this.tree = tree;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<ActionItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ActionItemDTO> items) {
		this.items = items;
	}

	public List<ActionItemOrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<ActionItemOrderDTO> orders) {
		this.orders = orders;
	}

	public List<NestedMenuItemDTO> getTree() {
		return tree;
	}

	public void setTree(List<NestedMenuItemDTO> tree) {
		this.tree = tree;
	}
}
