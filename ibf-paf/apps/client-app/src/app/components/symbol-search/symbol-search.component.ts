import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SymbolSearchService } from '@ibf-paf/core-data';
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'ibf-paf-symbol-search',
  templateUrl: './symbol-search.component.html',
  styleUrls: ['./symbol-search.component.scss']
})
export class SymbolSearchComponent implements OnInit {
  @Input() addButtonSuffix = '';

  form!: FormGroup;
  symbols$! : Observable<any>

  @Output() selectedSymbol = new EventEmitter<string>();

  constructor(private fb: FormBuilder, private symbolSearchService:SymbolSearchService) { }

  ngOnInit(): void {
    this.initForm()
  }

  initForm() {
    this.form = this.fb.group({
      keywords : ['']
    })
  }

  onSubmit() {
    console.log(this.form.value)
    const keywords = this.form.value.keywords;
    this.symbols$ = this.symbolSearchService.search(keywords);
  }

  addSymbol(symbol: string) {
    console.log("addSymbol:", symbol);
    this.selectedSymbol.emit(symbol);
  }

}
