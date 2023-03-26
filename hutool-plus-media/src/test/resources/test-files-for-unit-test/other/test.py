import os
import json
import pandas
from datetime import datetime
from typing import List


def get_current_datetime_str():
    return datetime.now().strftime('%Y-%m-%d_%H%M%S')   

def generate_insert_sql(table_name: str, colums: List[str], values: List[List[str]]) -> str:
    sql_lines = []
    column_list_str = ','.join(f'`{w}`' for w in colums)
    
    truncate_table_line = "truncate TABLE `{}`;".format(table_name)
    insert_header_line = "INSERT INTO `{}` ({}) VALUES".format(table_name, column_list_str)
    
    sql_lines.append(truncate_table_line)
    sql_lines.append(insert_header_line)
    
    count = 0
    for value in values:
        delimiter = ' ' if count % 1000 == 0 else ','
        
        transformed_value = []
        for item in value:
            if type(item) is str and not item.startswith('CAST(RAND()'):
                item = "'" + item + "'"
            transformed_value.append(item)
        
        line = '    ' + delimiter + '(' + ','.join(f"{w}" for w in transformed_value) + ')'
        sql_lines.append(line)
        count += 1
        
        if count % 1000 == 0:
            sql_lines.append(';\n')
            sql_lines.append(insert_header_line)
    sql_lines.append(';')
    
    return '\n'.join(sql_lines)

def write_sql_results_to_file(output_sql_file_path: str, sql_results: List) -> None:
    with open(output_sql_file_path, 'w', encoding='utf-8') as f:
        for line in sql_results:
            f.write(line)
            
def build_values_list(config: map, id_start: int) -> str:
    result_list = []
    
    for grid_prov_id, grid_prov_cfg in config.items():
        for bizsys_id, bizsys_cfg in grid_prov_cfg.items():
            for metric_type, metric_type_cfg in bizsys_cfg.items():
                date_range = metric_type_cfg['date_range']
                value_range_map = metric_type_cfg['value_range_map']
                
                random_value_gen_expr_map = {}
                for api_oper_type, value_range in value_range_map.items():
                    random_value_gen_expr_map[api_oper_type] = "CAST(RAND()*({high}-{low})+{low} AS UNSIGNED)".format(low = value_range[0], high = value_range[1])
                
                start_date = datetime.strptime(date_range[0], "%Y-%m-%d").date()
                end_date = datetime.strptime(date_range[1], "%Y-%m-%d").date()
                date_str_list = pandas.date_range(start_date, end_date, freq='d').strftime('%Y%m%d').tolist()
                for date_str in date_str_list:
                    date_int = int(date_str)
                    yearmonth_int = int(date_str[0:6])
                    year_int = int(date_str[0:4])
                    
                    for hour in range(0, 23):
                        for api_oper_type, random_value_gen_expr in random_value_gen_expr_map.items():
                            result_list.append([str(id_start), grid_prov_id, bizsys_id, int(metric_type), int(api_oper_type), hour, date_int, yearmonth_int, year_int, random_value_gen_expr])
                            id_start += 1

    return result_list



if __name__ == '__main__':
    working_dir = os.getcwd()
    current_datetime = get_current_datetime_str()
    output_sql_file_name = "test_api_metrics_data_{}.sql".format(current_datetime)
    output_sql_file_path = os.path.join(working_dir, output_sql_file_name)
    
    id_index_start = 10000
    
    config_small = {
        '1': {
            '9901': {
                '1': {
                    'date_range': ['2022-03-01', '2022-03-07'],
                    'value_range_map': {
                        '0': [15, 45],
                        '1': [20, 40],
                        '2': [1, 20],
                        '3': [10, 35],
                        '999': [1, 25],
                    }
                },
            },
            '9902': {
                '1': {
                    'date_range': ['2022-03-01', '2022-03-07'],
                    'value_range_map': {
                        '0': [30, 55],
                        '1': [50, 60],
                        '2': [1, 10],
                        '3': [60, 80],
                        '999': [5, 20],
                    }
                },
            },
            '9903': {
                '1': {
                    'date_range': ['2022-03-01', '2022-03-07'],
                    'value_range_map': {
                        '0': [20, 35],
                        '1': [60, 80],
                        '2': [5, 15],
                        '3': [5, 25],
                        '999': [1, 10],
                    }
                },
            },
            '9904': {
                '1': {
                    'date_range': ['2022-03-01', '2022-03-07'],
                    'value_range_map': {
                        '0': [5, 50],
                        '1': [15, 35],
                        '2': [1, 15],
                        '3': [10, 20],
                        '999': [1, 40],
                    }
                },
            },
            '9905': {
                '1': {
                    'date_range': ['2022-03-01', '2022-03-07'],
                    'value_range_map': {
                        '0': [30, 60],
                        '1': [20, 80],
                        '2': [1, 15],
                        '3': [10, 45],
                        '999': [20, 30],
                    }
                },
            }
        },
        
    }

    config_big = {
        '1': {
            '9901': {
                '1': {
                    'date_range': ['2010-01-01', '2022-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9902': {
                '1': {
                    'date_range': ['2013-01-01', '2021-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9903': {
                '1': {
                    'date_range': ['2011-01-01', '2017-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9904': {
                '1': {
                    'date_range': ['2012-01-01', '2016-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9905': {
                '1': {
                    'date_range': ['2008-01-01', '2019-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9906': {
                '1': {
                    'date_range': ['2018-01-01', '2022-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9907': {
                '1': {
                    'date_range': ['2013-01-01', '2015-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9908': {
                '1': {
                    'date_range': ['2011-01-01', '2014-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9909': {
                '1': {
                    'date_range': ['2012-01-01', '2016-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9910': {
                '1': {
                    'date_range': ['2013-01-01', '2021-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9911': {
                '1': {
                    'date_range': ['2016-01-01', '2022-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9912': {
                '1': {
                    'date_range': ['2019-01-01', '2021-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9913': {
                '1': {
                    'date_range': ['2010-01-01', '2013-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9914': {
                '1': {
                    'date_range': ['2015-01-01', '2016-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9915': {
                '1': {
                    'date_range': ['2012-01-01', '2013-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9916': {
                '1': {
                    'date_range': ['2011-01-01', '2012-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9917': {
                '1': {
                    'date_range': ['2020-01-01', '2022-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9918': {
                '1': {
                    'date_range': ['2009-01-01', '2012-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9919': {
                '1': {
                    'date_range': ['2011-01-01', '2014-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            },
            '9920': {
                '1': {
                    'date_range': ['2012-01-01', '2013-02-09'],
                    'value_range_map': {
                        '1': [20, 200],
                        '2': [20, 200],
                        '3': [10, 100],
                        '4': [50, 250],
                        '5': [30, 300]
                    }
                },
            }
        },
        
    }
    
    config = config_small
    # config = config_big
    
    table_name = 't_api_hourly_metrics'
    colums = [
        'id',
        'grid_province_id', 
        'bizsys_id', 
        'metric_type', 
        'api_oper_type', 
        'hour', 
        'date', 
        'year_month', 
        'year', 
        'metric_value'
    ]
    
    values = build_values_list(config, id_index_start)
    result = generate_insert_sql(table_name, colums, values)
   
    write_sql_results_to_file(output_sql_file_path, result)
    
    
    
    