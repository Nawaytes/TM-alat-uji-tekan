void setup_graph() {
  graph_master = cp5.addChart("loadcell_master")
    .setPosition(22, 118)
    .setSize(686, 368)
    .setRange(loadConfig("graph-range-l", "config.json"), loadConfig("graph-range-h", "config.json"))
    .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
    ;
  graph_master.getColor().setBackground(color(#003366));
  graph_master.setCaptionLabel(" ");

  graph_master.addDataSet("loadcell_m1");
  graph_master.setColors("loadcell_m1", color(yellow_), color(yellow_));
  graph_master.updateData("loadcell_m1", new float[loadConfig("graph-width", "config.json")]);
  
 
}
