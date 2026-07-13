let editingExpenseId = null;
let chartInstance = null;

window.onload = function () {

    loadExpenses();
    loadBudget();

    let hour = new Date().getHours();

    let greeting = "";

    if(hour < 12){
        greeting = "Good Morning ☀️";
    }
    else if(hour < 18){
        greeting = "Good Afternoon 🌤️";
    }
    else{
        greeting = "Good Evening 🌙";
    }

    let greetingElement =
        document.getElementById("greeting");

    if(greetingElement){
        greetingElement.innerText =
            greeting;
    }

    if(
        localStorage.getItem("darkMode")
        === "true"
    ){
        document.body.classList.add(
            "dark-mode"
        );
    }
};

async function setBudget() {

    let amount =
        document.getElementById(
            "budgetAmount"
        ).value;

    const response =
        await fetch(
            "http://localhost:8080/api/budget",
            {
                method:"POST",
                headers:{
                    "Content-Type":
                        "application/json"
                },
                body:JSON.stringify({
                    amount:amount
                })
            }
        );

    alert(
        await response.text()
    );

    loadBudget();
}

async function loadBudget() {

    const response =
        await fetch(
            "http://localhost:8080/api/budget/remaining"
        );

    const remaining =
        await response.text();

    document.getElementById(
        "remainingDisplay"
    ).innerText =
        "Rs " + remaining;

    if(
        Number(remaining) < 0
    ){

        document.getElementById(
            "warningMessage"
        ).innerHTML =
            "⚠ Budget Exceeded";

    }
    else{

        document.getElementById(
            "warningMessage"
        ).innerHTML =
            "✅ Safe";
    }
}

async function addExpense() {

    let title =
        document.getElementById(
            "expenseTitle"
        ).value;

    let amount =
        document.getElementById(
            "expenseAmount"
        ).value;

    let category =
        document.getElementById(
            "category"
        ).value;

    if(
        title === "" ||
        amount === ""
    ){
        alert(
            "Please fill all fields"
        );
        return;
    }

    if(
        editingExpenseId !== null
    ){

        const response =
            await fetch(
                `http://localhost:8080/api/expenses/${editingExpenseId}`,
                {
                    method:"PUT",
                    headers:{
                        "Content-Type":
                            "application/json"
                    },
                    body:JSON.stringify({
                        title:title,
                        amount:amount,
                        category:category
                    })
                }
            );

        alert(
            await response.text()
        );

        editingExpenseId = null;

        document.getElementById(
            "expenseTitle"
        ).value = "";

        document.getElementById(
            "expenseAmount"
        ).value = "";

        loadExpenses();
        loadBudget();

        return;
    }

    let today =
        new Date()
            .toISOString()
            .split("T")[0];

    const response =
        await fetch(
            "http://localhost:8080/api/expenses",
            {
                method:"POST",
                headers:{
                    "Content-Type":
                        "application/json"
                },
                body:JSON.stringify({
                    title:title,
                    amount:amount,
                    category:category,
                    date:today,
                    userId:1
                })
            }
        );

    alert(
        await response.text()
    );

    document.getElementById(
        "expenseTitle"
    ).value = "";

    document.getElementById(
        "expenseAmount"
    ).value = "";

    loadExpenses();
    loadBudget();
}

async function loadExpenses() {

    const response =
        await fetch(
            "http://localhost:8080/api/expenses"
        );

    const expenses =
        await response.json();

    renderChart(
        expenses
    );

    let tbody =
        document.querySelector(
            "#expenseTable tbody"
        );

    tbody.innerHTML = "";

    let totalExpenses = 0;

    expenses.forEach(expense => {

        totalExpenses +=
            Number(
                expense.amount
            );

        tbody.innerHTML +=

            `<tr>

                <td>
                    ${expense.title}
                </td>

                <td>
                    Rs ${expense.amount}
                </td>

                <td>
                    <span class="category-badge">
                        ${expense.category}
                    </span>
                </td>

                <td>
                    ${expense.date}
                </td>

                <td>

                    <button
                        class="edit-btn"
                        onclick="editExpense(
                            ${expense.id},
                            '${expense.title}',
                            '${expense.amount}',
                            '${expense.category}'
                        )">

                        Edit

                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteExpense(
                            ${expense.id}
                        )">

                        Delete

                    </button>

                </td>

            </tr>`;
    });

    document.getElementById(
        "expenseDisplay"
    ).innerText =
        "Rs " +
        totalExpenses;

    let counter =
        document.getElementById(
            "transactionCount"
        );

    if(counter){
        counter.innerText =
            expenses.length;
    }

    updateBudgetProgress(
        totalExpenses
    );
}

function updateBudgetProgress(
    totalExpenses
){

    let budgetText =
        document.getElementById(
            "budgetDisplay"
        )?.innerText || "0";

    let budget =
        parseFloat(
            budgetText.replace(
                "Rs",
                ""
            )
        );

    let progress =
        document.getElementById(
            "budgetProgress"
        );

    if(
        progress &&
        budget > 0
    ){

        let percentage =
            (totalExpenses /
                budget)
            * 100;

        progress.style.width =
            Math.min(
                percentage,
                100
            ) + "%";
    }
}

async function deleteExpense(id) {

    if(
        !confirm(
            "Delete this expense?"
        )
    ){
        return;
    }

    await fetch(
        `http://localhost:8080/api/expenses/${id}`,
        {
            method:"DELETE"
        }
    );

    loadExpenses();
    loadBudget();
}

function editExpense(
    id,
    title,
    amount,
    category
){

    editingExpenseId = id;

    document.getElementById(
        "expenseTitle"
    ).value = title;

    document.getElementById(
        "expenseAmount"
    ).value = amount;

    document.getElementById(
        "category"
    ).value = category;
}

window.editExpense =
    editExpense;

function searchExpenses() {

    let input =
        document.getElementById(
            "searchBox"
        ).value
        .toLowerCase();

    let rows =
        document.querySelectorAll(
            "#expenseTable tbody tr"
        );

    rows.forEach(row => {

        let text =
            row.innerText
                .toLowerCase();

        row.style.display =
            text.includes(input)
            ? ""
            : "none";
    });
}

function toggleDarkMode() {

    document.body.classList.toggle(
        "dark-mode"
    );

    localStorage.setItem(
        "darkMode",
        document.body.classList.contains(
            "dark-mode"
        )
    );
}

function scrollToSection(id){

    document.getElementById(id)
        .scrollIntoView({
            behavior:"smooth"
        });
}

function renderChart(
    expenses
){

    const canvas =
        document.getElementById(
            "expenseChart"
        );

    if(!canvas){
        return;
    }

    let categories = {};

    expenses.forEach(expense => {

        if(
            !categories[
                expense.category
            ]
        ){
            categories[
                expense.category
            ] = 0;
        }

        categories[
            expense.category
        ] += Number(
            expense.amount
        );

    });

    if(chartInstance){
        chartInstance.destroy();
    }

    chartInstance =
        new Chart(
            canvas,
            {
                type:"doughnut",

                data:{

                    labels:
                        Object.keys(
                            categories
                        ),

                    datasets:[
                        {
                            data:
                                Object.values(
                                    categories
                                )
                        }
                    ]
                }
            }
        );
}

function logout() {

    window.location.href =
        "login.html";
}