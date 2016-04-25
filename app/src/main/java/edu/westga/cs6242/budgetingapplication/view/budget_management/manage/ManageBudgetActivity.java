package edu.westga.cs6242.budgetingapplication.view.budget_management.manage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.cs6242.budgetingapplication.R;
import edu.westga.cs6242.budgetingapplication.dal.BudgetDatabaseHandler;
import edu.westga.cs6242.budgetingapplication.model.Bill;
import edu.westga.cs6242.budgetingapplication.model.Earning;
import edu.westga.cs6242.budgetingapplication.model.session.Session;
import edu.westga.cs6242.budgetingapplication.view.abstract_views.PortraitOnlyActivity;
import edu.westga.cs6242.budgetingapplication.view.budget_management.create.CreateBillActivity;
import edu.westga.cs6242.budgetingapplication.view.budget_management.create.CreateEarningActivity;

public class ManageBudgetActivity extends PortraitOnlyActivity {

    private BudgetDatabaseHandler dbh;
    private ArrayAdapter<Bill> billArrayAdapter;
    private ArrayAdapter<Earning> earningArrayAdapter;
    private TabHost tabHost;
    TextView titleLabel, descriptionLabel;
    EditText dateLabel;
    ListView lvBills, lvEarnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_budget);
        this.dbh = new BudgetDatabaseHandler(getApplicationContext(), null);
        this.tabHost = (TabHost) findViewById(R.id.tabHost);
        getViewsById();
        updateBudgetInformation();
        updateSessiontText();
        setUpTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_budget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_bill:
                this.createNewBill();
                return true;
            case R.id.new_earning:
                this.createNewEarning();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        this.updateList();
    }

    private void updateBudgetInformation() {
        updateList();
        titleLabel.setText(Session.getMonthlyBudget1().getTitle());
        descriptionLabel.setText(Session.getMonthlyBudget1().getDescription());
        dateLabel.setEnabled(false);
        dateLabel.setText(Session.getMonthlyBudget1().getDateCreated().toString());
    }

    private ArrayList<Bill> bills;
    private ArrayList<Earning> earnings;

    private void updateList() {
        setUpListViews();
        addOnClickListenerToBillsListView();
        addOnClickListenerToEarningsListView();
    }

    private void addOnClickListenerToEarningsListView() {
        this.lvEarnings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Earning earning = earnings.get(position);
                ToastMessage(earning.getTitle());
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_earnings);
                dialog.setTitle("View " + earning.getTitle());
                TextView tvTitle = (TextView) dialog.findViewById(R.id.tvEarningTitle);
                TextView tvAmount = (TextView) dialog.findViewById(R.id.tvEarningAmount);
                TextView tvDateEarned = (TextView) dialog.findViewById(R.id.tvDateEarned);
                TextView tvIsRecurring = (TextView) dialog.findViewById(R.id.tvIsRecurring);
                Button btnDelete = (Button) dialog.findViewById(R.id.btnDeleteEarning);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dbh.deleteEarningById(earning.getId())) {
                            ToastMessage("Deleted");
                            earnings.remove(earning);
                            dialog.hide();
                            earningArrayAdapter.notifyDataSetChanged();
                        }
                    }
                });
                tvTitle.setText(earning.getTitle());
                tvAmount.setText(Double.toString(earning.getAmount()));
                tvDateEarned.setText(earning.getDateEarned().toString());
                tvIsRecurring.setText((earning.isRecurring()) ? "Recurring" : "Not Recurring");
                dialog.show();
            }
        });
    }

    private void addOnClickListenerToBillsListView() {
        this.lvBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Bill bill = bills.get(position);
                ToastMessage(bill.getTitle());
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_bills);
                dialog.setTitle("View " + bill.getTitle());
                TextView tvBillTitle = (TextView) dialog.findViewById(R.id.tvBillTitle);
                TextView tvBillAmount = (TextView) dialog.findViewById(R.id.tvBillAmount);
                TextView tvDateDue = (TextView) dialog.findViewById(R.id.tvDateDue);
                TextView tvBillDatePaid = (TextView) dialog.findViewById(R.id.tvBillDatePaid);
                TextView tvBillIsRecurring = (TextView) dialog.findViewById(R.id.tvBillIsRecurring);
                TextView tvBillIsPaid = (TextView) dialog.findViewById(R.id.tvBillIsPaid);
                TextView btnDelete = (TextView) dialog.findViewById(R.id.btnDeleteBill);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dbh.deleteBillById(bill.getId())) {
                            bills.remove(bill);
                            billArrayAdapter.notifyDataSetChanged();
                            ToastMessage("Deleted");
                        }
                        dialog.hide();

                    }
                });
                tvBillTitle.setText(bill.getTitle());
                tvBillAmount.setText(Double.toString(bill.getAmount()));
                tvDateDue.setText(bill.getDateDue().toString());
                tvBillDatePaid.setText(bill.getDatePaid().toString());
                tvBillIsRecurring.setText((bill.isRecurring()) ? "Recurring" : "Not Recurring");
                tvBillIsPaid.setText((bill.isRecurring()) ? "Paid" : "Not Paid");
                dialog.show();
            }
        });
    }

    private void setUpListViews() {
        this.bills = this.dbh.getBillsByBudgetId(Session.getMonthlyBudget1().getId());
        this.earnings = this.dbh.getEarningsByBudgetId(Session.getMonthlyBudget1().getId());
        this.billArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bills);
        this.earningArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, earnings);
        this.lvBills.setAdapter(billArrayAdapter);
        this.lvEarnings.setAdapter(earningArrayAdapter);
    }

    private void getViewsById() {
        this.titleLabel = (TextView) findViewById(R.id.tvBudgetTitleLbl);
        this.descriptionLabel = (TextView) findViewById(R.id.etDescriptionLbl);
        this.dateLabel = (EditText) findViewById(R.id.etDateCreatedLbl);
        this.lvBills = (ListView) findViewById(R.id.lvBills);
        this.lvEarnings = (ListView) findViewById(R.id.lvEarnings);
    }

    private void updateSessiontText() {
        TextView txtSessionInfo = (TextView) findViewById(R.id.tvUserInformation);
        assert txtSessionInfo != null;
        String sessionString = "Signed in as: " + Session.getUser().getUserName();
        txtSessionInfo.setText(sessionString);
    }

    private void setUpTabs() {
        this.tabHost.setup();

        TabHost.TabSpec spec = this.tabHost.newTabSpec("Bills");
        spec.setContent(R.id.tabBills);
        spec.setIndicator("Bills");
        tabHost.addTab(spec);

        spec = this.tabHost.newTabSpec("Earnings");
        spec.setContent(R.id.tabEarnings);
        spec.setIndicator("Earnings");
        tabHost.addTab(spec);

        spec = this.tabHost.newTabSpec("Statistics");
        spec.setContent(R.id.tabStatistics);
        spec.setIndicator("Statistics");
        tabHost.addTab(spec);
    }

    private void createNewBill() {
        Intent intent = new Intent(getApplicationContext(), CreateBillActivity.class);
        startActivity(intent);
        updateList();
    }

    private void createNewEarning() {
        Intent intent = new Intent(getApplicationContext(), CreateEarningActivity.class);
        startActivity(intent);
        updateList();
    }

    private void ToastMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }
}
