/*
Template Name: Admin Pro Admin
Author: Wrappixel
Email: niravjoshi87@gmail.com
File: js
*/
$(function() {
    "use strict";
    // ==============================================================
    // Our Visitor
    // ==============================================================

  /*  var chart = c3.generate({
        bindto: '#admin',
        data: {
            url:"/admin/getC3Chart",
            type: 'post',
            onclick: function(d, i) { console.log("onclick", d, i); },
            onmouseover: function(d, i) { console.log("onmouseover", d, i); },
            onmouseout: function(d, i) { console.log("onmouseout", d, i); }
        },
        donut: {
            label: {
                show: false
            },
            title: "Admin",
            width: 20,

        },

        legend: {
            hide: true
        },
        color: {
            pattern: ['#1aaee1', '#24d2b5', '#6772e5']
        }
    });*/

        $.ajax({
            url:"/admin/getC3Chart",
            type:"post",
            success:function (data) {
                var chart=c3.generate({
                    bindto:'#admin',
                    data:{
                        columns: [
                            ['user', data['user']],
                            ['admin', data['admin']],
                        ],
                        type: 'donut',
                        onclick: function(d, i) { console.log("onclick", d, i); },
                        onmouseover: function(d, i) { console.log("onmouseover", d, i); },
                        onmouseout: function(d, i) { console.log("onmouseout", d, i); }
                    },
                    donut: {
                        label: {
                            show: false
                        },
                        title: "Admin/User",
                        width: 20,

                    },

                    legend: {
                        hide: true
                    },
                    color: {
                        pattern: ['#1aaee1', '#24d2b5']
                    }
                });
            }
        })

    $.ajax({
        url:"/admin/getChart",
        type:"post",
        success:function (data) {
            Morris.Area({
                element:'chart',
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