/*
Template Name: Admin Pro Admin
Author: Wrappixel
Email: niravjoshi87@gmail.com
File: js
*/
$(function() {

    $.ajax({
        url:"/file/getChart",
        type:"post",
        success:function (data) {
            Morris.Area({
                element:'upload-download-chart',
                data:data,
                xkey: 'days',
                ykeys: ['upload', 'download'],
                labels: ['上传', '下载'],
                pointSize: 0,
                fillOpacity: 0,
                pointStrokeColors: ['#20aee3', '#6972e3'],
                behaveLikeLine: true,
                gridLineColor: '#e0e0e0',
                lineWidth: 3,
                hideHover: 'auto',
                lineColors: ['#20aee3', '#6972e3'],
                resize: true
            });
        },
    });

});