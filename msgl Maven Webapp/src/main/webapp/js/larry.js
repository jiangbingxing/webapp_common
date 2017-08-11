
layui.config({
  base:'js/'
}).use(['jquery','element','layer','navtab'],function(){
    window.jQuery = window.$ = layui.jquery;
	  window.layer = layui.layer;
    var element = layui.element(),
         navtab = layui.navtab({
               elem: '.larry-tab-box'
         });

  
   //iframe自适应
  $(window).on('resize', function() {
	var totalHeight = $(window).height();
	var topHeight = $("div.layui-main").height();
	var buttomHeight = $("div.layui-footer").height();
	var tableHeight = $("ul.layui-tab-title").height();
    var $content = $('#larry-tab .layui-tab-content');
    $content.find('iframe').each(function() {
      $(this).height(totalHeight - topHeight - buttomHeight - tableHeight);
    });
    tab_W = $('#larry-tab').width();
    
    var larryFoot = $('#larry-footer').width();
    $('#larry-footer p.p-admin').width(larryFoot - 300);
  }).resize();
  
  // 左侧菜单导航-->tab
$(function(){
    // 注入菜单
    // var $menu = $('.larry-tab-menu');
    // console.log($menu);
    // $('#larry-tab .layui-tab-title').append($menu);
    $('#larry-nav-side').click(function(){
        if($(this).attr('lay-filter')!== undefined){
            $(this).children('ul').find('li').each(function(){
                var $this = $(this);
                if($this.find('dl').length > 0){
                   var $dd = $this.find('dd').each(function(){
                       $(this).click(function(){
                    	   var moduleId = $(this).attr('module-id');
                           var $a = $(this).children('a');
                           var href = $a.data('url');
                           var icon = $a.children('i:first').data('icon');
                           var title = $a.children('span').text();
                           var data = {
                                 href: href,
                                 icon: icon,
                                 title: title,
                                 tabId: moduleId,
                           }
                           navtab.mytabAdd(data);
                       });
                   });
                }else{
                    $this.click(function(){
                           var $a = $(this).children('a');
                           var moduleId = $(this).attr('module-id');
                           var href = $a.data('url');
                           var icon = $a.children('i:first').data('icon');
                           var title = $a.children('span').text();
                           var data = {
                                 href: href,
                                 icon: icon,
                                 title: title,
                                 tabId: moduleId,
                           }
                           navtab.mytabAdd(data);
                    });
                }
            });
        }
    })
})


});