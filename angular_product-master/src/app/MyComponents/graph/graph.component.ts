import { HttpClientModule } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.scss']
})
export class GraphComponent implements OnInit {
  dataset
  public chartDatasets: Array<any>
  public chartLabels: Array<any>
  public chartOptions: any

  public chartColors: Array<any>
  chartType;

  constructor(private userData:UserDataService,private http:HttpClientModule) { }
  @Input() datalab:Array<number>
  @Input() chartlab=[]
  @Input() tab
  ngOnInit(): void {

    if(this.tab==='Year')
     this.chartType = 'line' as const;
    else
    this.chartType = 'bar' as const;

  this.chartDatasets = [
    { data:this.datalab  }
  ];

  this.chartLabels = this.chartlab;

  this. chartColors = [
    {
      backgroundColor: [
        'rgb(238, 183, 107, 0.4)',
        'rgb(60, 65, 92, 0.4)',
        'rgb(149, 225, 211, 0.4)',
        'rgb(166, 214, 214, 0.4)',
        'rgb(161, 202, 226, 0.4)',
        'rgb(71, 89, 126, 0.4)',
        'rgb(119, 172, 241, 0.4)',
        'rgb(250, 135, 127, 0.4)',
        'rgb(255, 207, 99, 0.4)'
      ],
      borderColor: [
        'rgb(238, 183, 107, 1)',
        'rgb(60, 65, 92, 1)',
        'rgb(149, 225, 211, 1)',
        'rgb(166, 214, 214, 1)',
        'rgb(161, 202, 226, 1)',
        'rgb(71, 89, 126, 1)',
        'rgb(119, 172, 241, 1)',
        'rgb(250, 135, 127, 1)',
        'rgb(255, 207, 99, 1)'
      ],
      borderWidth: 1,
      fill:false
    }
  ];

  this.chartOptions = {
    scales: {
      yAxes: [
       {
        display: true,
        ticks: {
          stepSize: Math.ceil(Math.max(...this.datalab)/5),
          beginAtZero: true
       },
        scaleLabel: {
         display: true,
         labelString: "Number of Activities",
        },
       },
      ], xAxes: [
        {
         scaleLabel: {
          display: true,
          labelString: this.tab,
         },
        },
       ],},
    responsive: true
  };}
  public chartClicked(e: any): void { }
  public chartHovered(e: any): void { }
}
