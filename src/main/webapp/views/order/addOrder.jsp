<%@ page import="models.Discount" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Good" %>
<%@ page import="models.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new order</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Super app!</h1>
</div>

<div class="w3-container w3-padding">
    <%
        if (request.getAttribute("order") != null) {
            out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                    "   <h5>Order '" + request.getAttribute("order") + "' added!</h5>\n" +
                    "</div>");
        }
    %>
    <div class="w3-card-4">
        <div class="w3-container w3-center w3-green">
            <h2>Add Customer</h2>
        </div>
        <form method="post" action="/addOrder" class="w3-selection w3-light-grey w3-padding">
            <label>Date:
                <input type="text" name="orderDate" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br />
            </label>
            <label>Select Good:
                <select name="FK_good">
                    <% List<Good> listGood = (List<Good>) request.getAttribute("listGood");

                        for(int i =0; i < listGood.size(); i ++){

                            int id = listGood.get(i).getId();
                    %>
                    <option value="<%=id%>"> <%=listGood.get(i).getGoodName()%> </option>
                    <%
                        }
                    %>
                </select>
            </label>
            <label>Select Customer:
                <select name="FK_customer">
                    <% List<Customer> listCustomer = (List<Customer>) request.getAttribute("listCustomer");

                        for(int i =0; i < listCustomer.size(); i ++){

                            int id = listCustomer.get(i).getId();
                    %>
                    <option value="<%=id%>"> <%=listCustomer.get(i).getSurName()%> </option>
                    <%
                        }
                    %>
                </select>
            </label>
            <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Submit</button>
        </form>
    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='../..'">Back to main</button>
</div>
</body>
</html>