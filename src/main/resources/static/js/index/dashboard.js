/*
Template Name: Admin Pro Admin
Author: Wrappixel
Email: niravjoshi87@gmail.com
File: js
*/
$(function() {

    $.ajax({
        url:"/user/getC3Chart",
        type:"post",
        success:function (data) {
            var chart=c3.generate({
                bindto:'#files',
                data:{
                    columns: [
                        ['音频', data['audio']],
                        ['视频', data['vedio']],
                        ['文档', data['document']],
                        ['图片', data['picture']],
                        ['其他', data['other']],
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
                    title: "Upload Files",
                    width: 20,

                },

                legend: {
                    hide: true
                },
                color: {
                    pattern: ['#7460ee', '#ff9041','#20aee3','#24d2b5','#bcc3d3']
                }
            });
        }
    });

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
