<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Account ${account.id}</title>
    <script type="text/javascript">
        function submitForm(action) {
            document.accountForm.actionContext.value = action;
            document.accountForm.action = "<%=request.getContextPath()%>/accountDetails";
            document.accountForm.method = "POST";
            document.accountForm.submit();
        }
    </script>
</head>
<body>
<a href="<%=request.getContextPath()%>/accountsList">Back to the list</a>
<br/>
<table border="1">
    <tr>
        <td>Account ID</td>
        <td>${account.id}</td>
    </tr>
    <tr>
        <td>Owner</td>
        <td>${account.owner.name}</td>
    </tr>
    <tr>
        <td>Currency</td>
        <td>${account.currency}</td>
    </tr>
    <tr>
        <td>Amount</td>
        <td>${account.amount}</td>
    </tr>
</table>
<br/>
<form name="accountForm">
    <table>
        <tr>
            <td colspan="2">
                <input type="text" name="amountToAdd" size="20"/>
            </td>
            <td>
                <input type="button" value="Add" style="width: 100%;" onclick="submitForm('add')"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="text" name="amountToTake" size="20"/>
            </td>
            <td>
                <input type="button" value="Take" style="width: 100%;" onclick="submitForm('take')"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" name="amountToTransfer" size="20"/>
            </td>
            <td>
                <select name="accountSelect">
                    <c:forEach var="accountOption" items="${accountsForTransfer}">
                        <option>
                            <c:out value="${accountOption.id}"/>
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="button" value="Transfer" onclick="submitForm('transfer')"/>
            </td>
        </tr>
    </table>
    <input type="hidden" name="actionContext"/>
    <input type="hidden" name="accountId" value="${account.id}"/>
</form>
<br/>
<strong>Transactions list</strong>
<br/>
<table border="1">
    <tr>
        <td>Transaction ID</td>
        <td>Timestamp</td>
        <td>Amount</td>
    </tr>
    <c:forEach var="transaction" items="${transactionsList}">
        <tr>
            <td>${transaction.id}</td>
            <td>${transaction.date}</td>
            <td>${transaction.amount}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
