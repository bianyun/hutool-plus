#!/usr/bin/env bash

BSL_COLOR_START="\033[1;"
BSL_COLOR_END="\033[m"

get_color_code() {
  if [ ! -z "$1" ]; then
    echo "${BSL_COLOR_CODE_MAP[${1,,}]}"
  fi
}

colorize() {
  check_if_func_args_num_at_least 1 "color, msg"
  local color=$1

  if [ -z "$color" ]; then
    exit 1
  fi

  check_if_enum_type_func_param_value_valid "color, msg" "color"

  local color_code="$(get_color_code $color)"
  local text="${@:2}"

  echo "${BSL_COLOR_START}${color_code}${text}${BSL_COLOR_END}"
}

println_colored_text() {
  check_if_func_args_num_at_least 1 "color, msg"
  local color=$1

  check_if_enum_type_func_param_value_valid "color, msg" "color"

  println "$(colorize $color "${@:2}")"
}
