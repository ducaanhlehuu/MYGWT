package com.hello.client.activities.contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.hello.client.AppManager;
import com.hello.client.activities.basic.BasicViewImpl;
import com.hello.client.events.ActionEvent;
import com.hello.client.events.ActionEvent.Action;
import com.hello.shared.model.User;

public class ContactViewImpl extends BasicViewImpl implements ContactView {

    private static ContactViewImplUiBinder uiBinder = GWT.create(ContactViewImplUiBinder.class);
    private final CellTable<User> table = new CellTable<>();
    private final ListDataProvider<User> dataProvider = new ListDataProvider<>();
    private SimplePager pager;

    interface ContactViewImplUiBinder extends UiBinder<Widget, ContactViewImpl> {
    }

    @UiField
    Button btnAdd;
    @UiField
    HTMLPanel mainPanel;
    @UiField
    ListBox selectBox;
    @UiField(provided= true)
    SuggestBox searchBox;
    @UiField
    Button searchButton;

    public ContactViewImpl() {
        initTable();
        this.layout.getContainerPanel().add((uiBinder.createAndBindUi(this)));
        mainPanel.add(table);
        mainPanel.add(pager);
    }

    @Override
    public void showContact() {
//        table.setRowCount(ClientData.listUser.size(), true);
//        table.setRowData(0, ClientData.listUser);
        List<User> list = dataProvider.getList();
        if(list.size()>0) {
        	list.removeAll(list);
        }
        list.addAll(ClientData.listUser);
        table.redraw();
        
        MultiWordSuggestOracle oracle = (MultiWordSuggestOracle) searchBox.getSuggestOracle();
        oracle.clear();
        for(User user: ClientData.listUser) {
        	oracle.add(user.getName());
        }
    }

    @Override
    public Button getBtnAdd() {
        return btnAdd;
    }

    private void initTable() {
        TextColumn<User> nameColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getName();
            }
        };

        TextColumn<User> addressColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getAddress();
            }
        };

        TextColumn<User> usernameColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getUsername();
            }
        };

        TextColumn<User> phoneNumberColumn = new TextColumn<User>() {
            @Override
            public String getValue(User user) {
                return user.getPhoneNumber();
            }
        };

        Column<User, String> editColumn = new Column<User, String>(new ButtonCell()) {
            @Override
            public String getValue(User object) {
                return "";
            }

            @Override
            public void render(Context context, User object, SafeHtmlBuilder sb) {
                sb.appendHtmlConstant("<i class=\"fas fa-edit fa-x\" style=\"cursor:pointer\"></i>");
            }
        };
        editColumn.setFieldUpdater(new FieldUpdater<User, String>() {
            @Override
            public void update(int index, User object, String value) {
                AppManager.CLIENT_FACTORY.getEventBus().fireEvent(new ActionEvent(Action.EDIT, object));
            }
        });

        Column<User, String> deleteColumn = new Column<User, String>(new ButtonCell()) {
            @Override
            public String getValue(User object) {
                return "";
            }

            @Override
            public void render(Context context, User object, SafeHtmlBuilder sb) {
                sb.appendHtmlConstant("<i class=\"fa-solid fa-trash\" aria-hidden=\"true\" style=\"cursor:pointer\"></i>");
            }
        };
        deleteColumn.setFieldUpdater(new FieldUpdater<User, String>() {
            @Override
            public void update(int index, User object, String value) {
                AppManager.CLIENT_FACTORY.getEventBus().fireEvent(new ActionEvent(Action.DELETE, object));
            }
        });

        table.addColumn(usernameColumn, "Username");
        table.addColumn(nameColumn, "Name");
        table.addColumn(phoneNumberColumn, "Phone Number");
        table.addColumn(addressColumn, "Address");
        table.addColumn(editColumn, "Edit");
        table.addColumn(deleteColumn, "Delete");
        
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);

        ListHandler<User> sortHandler = new ListHandler<>(dataProvider.getList());
        sortHandler.setComparator(usernameColumn, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getUsername().compareTo(u2.getUsername());
            }
        });
        table.addColumnSortHandler(sortHandler);

        usernameColumn.setSortable(true);
        table.getColumnSortList().push(usernameColumn);


        table.setPageSize(10);

        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        pager = new SimplePager(TextLocation.CENTER, pagerResources, true, 0, true);
        pager.setDisplay(table);

        dataProvider.addDataDisplay(table);
        pager.setPageSize(10);
        
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        searchBox = new SuggestBox(oracle);
    }

    @Override
    public ListBox getSelectBox() {
        return selectBox;
    }

    @Override
    public Button getSearchButton() {
        return searchButton;
    }

    @Override
    public SuggestBox getSearchBox() {
        return searchBox;
    }

    @Override
    public void showContact(List<User> users) {
//        table.setRowCount(users.size(), true);
//        table.setRowData(0, users);
    	List<User> list = dataProvider.getList();
        if(list.size()>0) {
        	list.removeAll(list);
        }
        list.addAll(users);
        table.redraw();
    }
}
