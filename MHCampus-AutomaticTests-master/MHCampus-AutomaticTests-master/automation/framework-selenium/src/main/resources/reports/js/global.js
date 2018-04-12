$(document).ready(function(){
    var groupedFailures = $('.grouped_failure');
    groupedFailures.each(function(){
        var $this = $(this);
        var detailBtn = $this.find('.detail_btn');
        var row = $this.find('.row');
        var details = $this.find('.details');
        detailBtn.click(function(){
            row.toggleClass('open');
            details.toggle();
        });
    });
});