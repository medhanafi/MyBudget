 var api_url = 'http://192.168.1.3:8085'

 $(document).ready(function() {

     token = getUrlParameter("token");
     if (!isEmpty(token)) {
         getCategories(token, "revenues");
         getCategories(token, "fixes");
         getCategories(token, "variables");
     }

 });

 $(document).on('click', '#btnLogin', function() {

     if (isEmpty($('#userName').val())) {
         $('#userName').addClass('is-invalid')
     } else {
         $('#userName').removeClass('is-invalid');
         $('#userName').addClass('is-valid')
     }

     login($('#userName').val(), $('#userPassword').val())

 });
 $(document).on('click', '.item_id', function() {

     console.info($(this).data('id'));
     $('#titlePage').html($(this).data('title'));
     $('#idSubCat').val($(this).data('id'));

     // getItemsBySubCat(token, $(this).data('id'));
     loadItemData($(this).data('id'))

 });

 function login(userName, userPassword) {
     var body = {
         grant_type: 'password',
         username: userName,
         password: userPassword
     };
     $.ajax({
         type: "POST",
         url: api_url + "/oauth/token",
         dataType: 'json',
         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
         async: true,
         data: body,
         beforeSend: function(xhr) {
             xhr.setRequestHeader('Authorization', make_base_auth("tasks-client_id", "tasks-CLPWD-T@sk_kmS0ft"));
         },
         success: function(result) {
             //console.info(result);
             getUser(userName, result.access_token)
         },
         error: function(result) {
             //console.info(result);
         },
         complete: function(result) {
             //console.info(result);
         }
     });
 }

 function getUser(userName, token) {

     $.ajax({
         type: "GET",
         url: api_url + "/api/users/user_by_name/" + userName,
         dataType: 'json',
         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
         async: true,
         headers: {
             "Authorization": "Bearer " + token
         },
         success: function(result) {
             window.location.href = "http://localhost/mybudget-client/view/home.html?user_name=" + result.username + " (" + result.family.code + ")&token=" + token;
         },
         error: function(result) {
             console.info(result);
         },
         complete: function(result) {
             //console.info(result);
         }
     })
 }




 function getCategories(token, categoryType) {
     $.ajax({
         type: "GET",
         url: api_url + "/api/categories/" + categoryType,
         dataType: 'json',
         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
         async: true,
         headers: {
             "Authorization": "Bearer " + token
         },
         success: function(result) {
             listParent = "";
             result.forEach(function(item) {

                 listParent = listParent + "<a href='#' data-toggle='collapse' data-target='#toggleDemo" + item.catId + "' data-parent='#idList_" + categoryType + "' class='collapsed list-group-item'>" +
                     item.catLabel + "</a> ";

                 listChild = " <div class='list-group collapse' id='toggleDemo" + item.catId + "' style='height: 0px; margin-bottom: 0px;'>";
                 item.subCategories.forEach(function(subItem) {
                     listChild = listChild + "<a href='#' class='list-group-item item_id'  data-id='" + subItem.subCatId + "' data-title=\"" + item.catLabel + " : " + subItem.subCatLabel + "\"'>&nbsp;&nbsp;<span class='glyphicon glyphicon-chevron-right'></span>&nbsp;" + subItem.subCatLabel + "</a>";
                 });
                 listParent = listParent + listChild + "</div>";

             });
             $("#idList_" + categoryType).html(listParent + "</div>");

         },
         error: function(result) {
             console.info(result);
         },
         complete: function(result) {

         }
     });
 }



 function getItemsBySubCat(token, subcat) {
     $.ajax({
         type: "GET",
         url: api_url + "/api/itemsBySubcat/?month=3&subcat_id=" + subcat,
         dataType: 'json',
         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
         async: true,
         headers: {
             "Authorization": "Bearer " + token
         },
         success: function(result) {
             tableBody = "";
             result.forEach(function(item) {

                 tableBody = tableBody +
                     "<tr>" +
                     "<td>" + item.itemId + "</td>" +
                     "<td>" + item.itemLabelle + "</td>" +
                     "<td>" + item.expectedAmount + "</td>" +
                     "<td>" + item.expectedQuantity + "</td>" +
                     "<td>" + item.dateItem + "</td>" +
                     "<td>" + item.itemStatus + "</td>" +
                     "</tr>";

             });
             $('#itemDataTable_').DataTable();

         },
         error: function(result) {
             console.info(result);
         },
         complete: function(result) {

         }
     });
 }


 function make_base_auth(user, password) {
     var tok = user + ':' + password;
     var hash = btoa(tok);
     return "Basic " + hash;
 }

 function isEmpty(el) {
     return $.trim(el) == ''
 }


 var getUrlParameter = function getUrlParameter(sParam) {
     var sPageURL = window.location.search.substring(1),
         sURLVariables = sPageURL.split('&'),
         sParameterName,
         i;

     for (i = 0; i < sURLVariables.length; i++) {
         sParameterName = sURLVariables[i].split('=');

         if (sParameterName[0] === sParam) {
             return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
         }
     }
 };

 function loadItemData(idSubCat) {
     $('#itemDataTable').DataTable({
         destroy: true,
         searching: true,
         "ajax": {
             "url": api_url + "/api/itemsBySubcat/?month=3&subcat_id=" + idSubCat,
             "dataSrc": '',
             "headers": {
                 "Authorization": "Bearer " + token
             }
         },
         "language": {
             "decimal": "",
             "emptyTable": "Aucun donnée disponible",
             "info": "Affichage de _START_ à _END_ sur _TOTAL_ lignes au total",
             "infoEmpty": "Affichage de 0 à 0 sur 0 lignes au total",
             "infoFiltered": "(filtré sur _MAX_ lignes au total)",
             "infoPostFix": "",
             "thousands": ",",
             "lengthMenu": "Afficher _MENU_ lignes",
             "loadingRecords": "Chargement...",
             "processing": "Encours...",
             "search": "Recherche:",
             "zeroRecords": "Aucun lignes trouvé",
             "paginate": {
                 "first": "Premier",
                 "last": "Dernier",
                 "next": "Suivant",
                 "previous": "Precedent"
             },
             "aria": {
                 "sortAscending": ": activate to sort column ascending",
                 "sortDescending": ": activate to sort column descending"
             }
         },
         columns: [
             { data: 'itemId' },
             { data: 'itemLabelle' },
             { data: 'expectedAmount' },
             { data: 'expectedQuantity' },
             { data: 'dateItem' },
             {
                 data: 'status',
                 render: function(data, type, row) {
                     if (data === 'innactif') {
                         return "<span class=\"label label-danger\">innactif</span>";
                     } else {
                         return "<span class=\"label label-success\">actif</span>";
                     }
                 }
             }
         ]
     });

 }