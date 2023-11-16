"use strict"
/*let initList = function () {

  let savedList = window.localStorage.getItem("todos");
  if (savedList != null)
    todoList = JSON.parse(savedList);
  else
    //code creating a default list with 2 items
    todoList.push(
      {
        title: "Learn JS",
        description: "Create a demo application for my TODO's",
        place: "445",
        dueDate: new Date(2019, 10, 16)
      },
      {
        title: "Lecture test",
        description: "Quick test from the first three lectures",
        place: "F6",
        dueDate: new Date(2019, 10, 17)
      }
      // of course the lecture test mentioned above will not take place
    );
}*/

//initList();

const BASE_URL = "https://api.jsonbin.io/v3/b/653243a112a5d376598e29ba ";
const SECRET_KEY = "$2a$10$SDg.eOZNYeybzYGjPo84GemsKOze3NS32iG/TgQKxx3afxMjkAYPm";
let todoList = [];
let todoListCopy = []; //declares a new array for Your todo list
$.ajax({
  // copy Your bin identifier here. It can be obtained in the dashboard
  url: BASE_URL,
  type: 'GET',
  headers: { //Required only if you are trying to access a private bin
    'X-Master-Key': SECRET_KEY
  },
  success: (data) => {
    todoList = data.record;
  },
  error: (err) => {
    console.log(err.responseJSON);
  }
});

let updateJSONbin = function () {
  $.ajax({
    url: BASE_URL,
    type: 'PUT',
    headers: { //Required only if you are trying to access a private bin
      'X-Master-Key': SECRET_KEY
    },
    contentType: 'application/json',
    data: JSON.stringify(todoList),
    success: (data) => {
      console.log(data);
    },
    error: (err) => {
      console.log(err.responseJSON);
    }
  });
}

function formatDateToYYYYMMDD(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}


let updateTodoList = function () {
  let todoListTable = $("#todoListTable");
  let buttonContainer = $("#buttonContainer");
  let filterInput = $("#inputSearch");

  // Remove all elements
  todoListTable.empty();
  buttonContainer.empty();

  // Add all elements
  for (let todo in todoList) {
    let newRow = $("<tr>");
    todoListTable.append(newRow);
    const keys = Object.keys(todoList[todo]);
    for (const key of keys) {
      let text = '' + todoList[todo][key];
      if (key === 'dueDate') {
        text = text.substring(0, 10);
      }
      let newCell = $("<td>").text(text);
      newRow.append(newCell);
    }

    let newDeleteButton = $("<input>")
      .attr("type", "button")
      .attr("value", "Delete")
      .addClass("btn btn-warning")
      .click(function () {
        deleteTodo(todo);
      });
    let deleteButtonCell = $("<td>");
    deleteButtonCell.append(newDeleteButton);
    newRow.append(deleteButtonCell);
  }

  // Add all elements
  for (let i = 0; i < todoList.length; i++) {
    if (
      (filterInput.val() !== '' && todoList[i].title.includes(filterInput.val())) ||
      (filterInput.val() !== '' && todoList[i].description.includes(filterInput.val()))
    ) {
      let highlightedRow = todoListTable.find("tr").eq(i);

      // Iterate through the cells in the row and add the class to each cell
      highlightedRow.find("td").addClass("bg-secondary");
    }
  }
}

setInterval(updateTodoList, 1000);

let deleteTodo = function (index) {
  todoList.splice(index, 1);
  updateJSONbin();
}

let addTodo = function () {
  //get the elements in the form
  let inputTitle = $("#inputTitle");
  let inputDescription = $("#inputDescription");
  let inputPlace = $("#inputPlace");
  let inputDate = $("#inputDate");
  //get the values from the form
  let newTitle = inputTitle.val();
  let newDescription = inputDescription.val();
  let newPlace = inputPlace.val();
  let newDate = formatDateToYYYYMMDD(new Date(inputDate.val()));
  //create new item
  let newTodo = {
    title: newTitle,
    description: newDescription,
    place: newPlace,
    dueDate: newDate
  };
  //add item to the list
  todoList.push(newTodo);
  //window.localStorage.setItem("todos", JSON.stringify(todoList));
  updateJSONbin();
  inputTitle.val('');
  inputDescription.val('');
  inputPlace.val('');
  inputDate.val('');
}

let sort = function(){
  const startDate = new Date($("#inputDateFrom").val());
  const endDate = new Date($("#inputDateTo").val());

  const filteredData = todoList.filter(item => {
    const dueDate = new Date(item.dueDate);
    return dueDate >= startDate && dueDate <= endDate;
  });

  filteredData.sort((a, b) => a.dueDate - b.dueDate);
  todoListCopy = todoList;
  todoList = filteredData;

  let newDiv = $("<div>")
    .text("Consider you are seeing filtered data only")
    .addClass("text-center text-white bg-danger rounded p-3");

  $("#tableContainer").append(newDiv);

}

let reset = function (){
  todoList = todoListCopy;
  $("#tableContainer").children().last().remove();

}

