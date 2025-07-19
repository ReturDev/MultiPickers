<div align="center">
  <a href="" target="_blank">
    <img alt="Multipickers-Banner" src="https://github.com/ReturDev/ReTurDev/assets/79228896/8a662f94-4814-4584-9e93-9ae1c1961bc1" width="600"/>
  </a>
  
  [![Release](https://img.shields.io/badge/Release-v1.0.1-%231afc05?style=flat)](https://jitpack.io/#ReturDev/MultiPickers)
  [![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
  [![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)
  
</div>

---

  **The Multipicker library features a collection of easily implementable composable pickers.**
  
  Current pickers:
  
  - **TextPicker**
  - **NumberPicker**
  - **TimePicker**

</br>

## Setup :wrench:

### Gradle KTS
- Add it in root build.gradle
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {url = uri("https://jitpack.io")}
    }
}
```
- Add the dependency

```
      implementation("com.github.ReturDev:MultiPickers:1.0.1")
```
</br>

## Examples :eyes:

<table>
  <tr>
    <td align="center" width="500">
      <p><strong>Short Picker</strong></p>
      <img src="https://github.com/ReturDev/MultiPickers/assets/79228896/9a20edb7-1bcd-44ad-b859-890e3e8e5353" width="300" height="400" />
    </td>
    <td align="center" width="500">
      <p><strong>Long Picker</strong></p>
      <img src="https://github.com/ReturDev/MultiPickers/assets/79228896/c3b0e5a1-5fbf-4784-af34-951b1f20eab4" width="300" height="400" />
    </td>
  </tr>
    <tr>
    <td align="center">
      <p><strong>Time Picker</strong></p>
      <img src="https://github.com/ReturDev/MultiPickers/assets/79228896/64d0c32c-256c-45da-b261-8965d7c260d6" width="300" height="400" />
    </td>
    <td align="center">
      <p><strong>Picker with colorful selection</strong></p>
      <img src="https://github.com/ReturDev/MultiPickers/assets/79228896/2493e355-a983-4e5a-93ab-335904db5878" width="300" height="400" />
    </td>
  </tr>
</table>

</br>

## Implementation :pencil2:

- ### TextPicker

```
@Composable
fun TextPicker() {
    val textList = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten")

    val pickerState = rememberSaveablePickerState(
        itemsCount = textList.size
    )

    TextPicker(items = textList, pickerState = pickerState)

}
```

- ### NumberPicker

```
@Composable
fun NumberPicker() {
    val numberList = (0..100).toList()
    val pickerState = rememberSaveablePickerState(
        itemsCount = numberList.size,
        pickerLength = PickerLength.Long
    )

    NumberPicker(items = numberList, pickerState = pickerState)

}
```

- ### TimePicker

```
@Composable
fun TimePickerPreview() {

    val pickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 30,
        initialSecond = 0,
        pickerLength = PickerLength.LONG
    )

    TimePicker(pickerState = pickerState)

}
```

## License :page_facing_up:

Copyright 2024 ReturDev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   > http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
