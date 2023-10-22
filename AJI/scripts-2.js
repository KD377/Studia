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
let todoList = []; //declares a new array for Your todo list
$.ajax({
  // copy Your bin identifier here. It can be obtained in the dashboard
  url: BASE_URL,
  type: 'GET',
  headers: { //Required only if you are trying to access a private bin
    'X-Master-Key': SECRET_KEY
  },
  success: (data) => {
    console.log(data);
    todoList = data.record;
    console.log(todoList[0])
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


let updateTodoList = function () {
  let todoListTable = document.getElementById("todoListTable");
  let buttonContainer = document.getElementById("buttonContainer");

  //remove all elements
  while (todoListTable.firstChild) {
    todoListTable.removeChild(todoListTable.firstChild);
    buttonContainer.innerHTML='';
  }


  //add all elements
  for (let todo in todoList) {
    let newRow = document.createElement("tr");
    todoListTable.appendChild(newRow);
    const keys = Object.keys(todoList[todo]);
    for (const key of keys) {
      let text = ''+todoList[todo][key];
      if (key === 'dueDate')
      {
        text = text.substring(0,10);
      }
      let newCell = document.createElement("td");
      if (key === 'title')
      {
        newCell.className = "text-uppercase";
      }
      newCell.textContent = text;
      newRow.appendChild(newCell);   
    }


    let newDeleteButton = document.createElement("input");
    newDeleteButton.type = "button";
    newDeleteButton.value = "Delete";
    newDeleteButton.className = "btn btn-danger";
    newDeleteButton.addEventListener("click",
      function () {
        deleteTodo(todo);
      });
    let deleteButtonCell = newRow.insertCell();
    deleteButtonCell.appendChild(newDeleteButton);
    //buttonContainer.appendChild(newDeleteButton);
    //todoListDiv.appendChild(newElement);
  }

  //add all elements
  let filterInput = document.getElementById("inputSearch");
  for (let todo in todoList) {
    if (
      (filterInput.value == "") ||
      (todoList[todo].title.includes(filterInput.value)) ||
      (todoList[todo].description.includes(filterInput.value))
    ) {
      let newElement = document.createElement("p");
      let newContent = document.createTextNode(todoList[todo].title + " " +
        todoList[todo].description);
      newElement.appendChild(newContent);
      //todoListDiv.appendChild(newElement);
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
  let inputTitle = document.getElementById("inputTitle");
  let inputDescription = document.getElementById("inputDescription");
  let inputPlace = document.getElementById("inputPlace");
  let inputDate = document.getElementById("inputDate");
  //get the values from the form
  let newTitle = inputTitle.value;
  let newDescription = inputDescription.value;
  let newPlace = inputPlace.value;
  let newDate = new Date(inputDate.value);
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
}

