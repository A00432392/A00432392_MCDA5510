using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using Serilog;

namespace Assignment_3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void customersBindingNavigatorSaveItem_Click(object sender, EventArgs e)
        {
            this.Validate();
            this.customersBindingSource.EndEdit();
            this.tableAdapterManager.UpdateAll(this.database1DataSet);

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            // TODO: This line of code loads data into the 'database1DataSet.Customers' table. You can move, or remove it, as needed.
            Log.Information("Loading form");
            this.customersTableAdapter.Fill(this.database1DataSet.Customers);
            validate();

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void bindingNavigatorMoveNextItem_Click(object sender, EventArgs e)
        {
            Log.Information("Next item");
            validate();
        }

        private void bindingNavigatorMovePreviousItem_Click(object sender, EventArgs e)
        {
            Log.Information("Previous item");
            validate();
        }

        private void validate()
        {
            label1.Text = "";
            if (!Regex.IsMatch(postalCodeTextBox.Text, @"^[A-Za-z]\d[A-Za-z][ -]?\d[A-Za-z]\d$")) {
                label1.Text = "Postal code is having error.";
            }
            if (!Regex.IsMatch(phoneNumberTextBox.Text, @"\(?\d{3}\)?-? *\d{3}-? *-?\d{4}"))
            {
                label1.Text = "Phone number is having errors.";
            }
            if (!Regex.IsMatch(emailAddressTextBox.Text, @"^([0-9a-zA-Z]([-\.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$"))
            {
                label1.Text = "Please enter a valid email id";
            }


            Log.Error(label1.Text);
        }
    }
}
