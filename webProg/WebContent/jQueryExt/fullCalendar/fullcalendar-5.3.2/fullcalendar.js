// 1. title, start_date, end_date 가지고 오는 method
// 2. json 타입으로 [{title:값, start:값, end:값},{},{}]
// 3. events: javascript의 object타입으로 할당.
window.onclick = function (event) {
  console.log(event);
  let modal = document.getElementById('id01');
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

var calendar;

function showModal(arg) {

  let saveBtn = document.getElementById('saveBtn');
  saveBtn.onclick = function () {
    title = document.getElementById('title').value;
    start = document.getElementById('startDate').value;
    end = document.getElementById('endDate').value;
    //console.log(arg.start);
    let st = arg.start;
    start = new Date(st.getFullYear(), st.getMonth(), st.getDate(), start.substring(0, 2), start.substring(3, 5));
    end = new Date(st.getFullYear(), st.getMonth(), st.getDate(), end.substring(0, 2), end.substring(3, 5));
    console.log(start, end);
    start = start.getFullYear() + '-' + (start.getMonth() + 1) + '-' + start.getDate() + 'T' + start.getHours() + ':' + start.getMinutes() + ':00';
    end = end.getFullYear() + '-' + (end.getMonth() + 1) + '-' + end.getDate() + 'T' + end.getHours() + ':' + end.getMinutes() + ':00';
    if (title) {
      calendar.addEvent({
        title: title,
        start: start, //arg.start,
        end: end, //arg.end,
        allDay: false //arg.allDay
      });
      // db에 새로운 한건 등록. -> 서블릿
      console.log(title, start, end);
      createSchedule(title, start, end);
    }
    calendar.unselect();
  }
} // end of showModal()

let events = [];

//function contentLoadFunc() {

document.addEventListener('DOMContentLoaded', function () {
  var calendarEl = document.getElementById('calendar');

  let xhtp = new XMLHttpRequest();
  xhtp.onreadystatechange = function () {
    if (xhtp.readyState == 4 && xhtp.status == 200) {
      //console.log(xhtp.response);
      let data = xhtp.response;
      data.forEach(function (item) {
        events.push(item);
      });
      // events 에 값을 할당하고 그다음..contentLoadFunc()호출.
      calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        initialDate: '2020-11-12',
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        selectMirror: true,
        select: function (arg) {
          // var title = prompt('스케줄 등록하세요.:');
          // var start = prompt('시작일정:');
          // var end = prompt('종료일정:');
          var title;
          let modelPage = document.getElementById('id01');
          modelPage.style.display = "block";
          showModal(arg);

          if (title) {
            calendar.addEvent({
              title: title,
              start: start, //arg.start,
              end: end, //arg.end,
              allDay: false //arg.allDay
            });
            // db에 새로운 한건 등록. -> 서블릿
            console.log(title, start, end);
            createSchedule(title, start, end);
          }
          calendar.unselect()
        },
        eventClick: function (arg) {
          if (confirm('Are you sure you want to delete this event?')) {
            arg.event.remove();
            let start = arg.event.start;
            let title = arg.event.title;
            if (start.getDate() < 10)
              start = start.getFullYear() + '-' + (start.getMonth() + 1) + '-0' + start.getDate();
            else
              start = start.getFullYear() + '-' + (start.getMonth() + 1) + '-' + start.getDate();

            console.log(arg);
            // db에 한건 삭제. -> 서블릿
            deleteSchedule(title, start);
          }
        },
        editable: true,
        dayMaxEvents: true, // allow "more" link when too many events
        events: events
      }); // calendar() 메소드 호출.

      calendar.render();
    }
  }

  xhtp.responseType = 'json';
  xhtp.open('get', '../../GetScheduleServlet');
  xhtp.send();

});

//} // end of contentLoadFunc()

function createSchedule(v1, v2, v3) {
  let xhtp = new XMLHttpRequest();
  xhtp.onreadystatechange = function () {
    if (xhtp.readyState == 4 && xhtp.status == 200) {
      console.log(xhtp);
    }
  }
  xhtp.open('get', '../../PutScheduleServlet?title=' + v1 + '&start=' + v2 + '&end=' + v3);
  xhtp.send();
}

function deleteSchedule(v1, v2) {
  let xhtp = new XMLHttpRequest();
  xhtp.onreadystatechange = function () {
    if (xhtp.readyState == 4 && xhtp.status == 200) {
      console.log(xhtp);
    }
  }
  xhtp.open('get', 'DelScheduleServlet?title=' + v1 + '&start=' + v2);
  xhtp.send();
}