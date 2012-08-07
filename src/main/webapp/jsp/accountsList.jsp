<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Accounts of ${user.name}</title>
    <script type="text/javascript">
        function submitForm(action, accountId)
        {
            if(action == 'insert')
            {
                document.accountsForm.accountCurrency.value =  document.accountsForm.currencyInsert.value;
            }
            else if(action == 'delete')
            {
                document.accountsForm.accountId.value =  accountId;
            }
            document.accountsForm.actionContext.value =  action;
            document.accountsForm.action = "<%=request.getContextPath()%>/accountsList";
            document.accountsForm.method = "POST";
            document.accountsForm.submit();
        }
    </script>
</head>
<body>
<form name="accountsForm">
    <table border="1">
        <tr>
            <td><strong>Account ID</strong></td>
            <td><strong>Owner</strong></td>
            <td><strong>Currency</strong></td>
            <td><strong>Amount</strong></td>
            <td><strong> </strong></td>
        </tr>
        <c:forEach var="account" items="${accountsList}">
            <tr>
                <td>
                    <a href="<%=request.getContextPath()%>/accountDetails?accountId=${account.id}">
                        ${account.id}
                    </a>
                </td>
                <td>${account.owner.name}</td>
                <td>${account.currency}</td>
                <td>${account.amount}</td>
                <td><input type="button" value="Delete" onclick="submitForm('delete',${account.id})"/></td>
            </tr>
        </c:forEach>
        <tr>
            <td> </td>
            <td>${user.name}</td>
            <td>
                <select name="currencyInsert">
                    <c:forEach var="currency" items="${currencies}">
                        <option>${currency}</option>
                    </c:forEach>
                </select>
            </td>
            <td> </td>
            <td><input type="button" value="Insert" onclick="submitForm('insert')"/></td>
        </tr>
    </table>
    <input type="hidden" name="accountId"/>
    <input type="hidden" name="accountCurrency"/>
    <input type="hidden" name="actionContext"/>
</form>
</body>
</html>