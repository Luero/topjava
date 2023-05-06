const mealsAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealsAjaxUrl
};

$(function () {
        makeEditable(
            $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
    );
});

var filter="";

function getFilter() {
    $.ajax({
        url: ctx.ajaxUrl + "filter/?" + $('#filterForm').serialize(),
        type: "GET"
    }).done(function (data) {
        clearAndUpdateTable(data);
        filter = "filter/?" + $('#filterForm').serialize();
    });
}

function resetFilter() {
    filter="";
    updateTable();
}

function updateTable() {
    $.get(ctx.ajaxUrl + filter, function (data) {
        clearAndUpdateTable(data);
    });
}



